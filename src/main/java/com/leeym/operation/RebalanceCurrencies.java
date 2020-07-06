package com.leeym.operation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.leeym.api.accounts.Account;
import com.leeym.api.accounts.AccountId;
import com.leeym.api.accounts.AccountsApi;
import com.leeym.api.accounts.BalanceCurrency;
import com.leeym.api.accounts.Conversion;
import com.leeym.api.accounts.CurrencyPairs;
import com.leeym.api.profiles.ProfileId;
import com.leeym.api.quotes.Quote;
import com.leeym.api.quotes.QuotesApi;
import com.leeym.api.rates.Rate;
import com.leeym.api.rates.RatesApi;
import com.leeym.common.Amount;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.leeym.api.Currencies.CNY;
import static com.leeym.api.Currencies.EUR;
import static com.leeym.api.Currencies.GBP;
import static com.leeym.api.Currencies.HKD;
import static com.leeym.api.Currencies.JPY;
import static com.leeym.api.Currencies.PHP;
import static com.leeym.api.Currencies.TWD;
import static com.leeym.api.Currencies.USD;
import static com.leeym.api.accounts.Account.Balance;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static java.util.logging.Level.CONFIG;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;

/**
 * existing:   the balances currently in the account
 * equivalent: the existing amount calculated in USD
 * ideal:      the portfolio we originally want, though some currencies may be unsupported or unwanted.
 * optimal:    the actual portfolio we can have
 */
public class RebalanceCurrencies implements Callable<List<String>> {

    private static final double THRESHOLD = 0.05;
    private static final Set<Currency> UNWANTED_CURRENCIES = ImmutableSet.of(CNY, HKD);
    private static final Map<Currency, Double> IDEAL_PORTFOLIO = ImmutableMap.<Currency, Double>builder()
            .put(USD, 30.0)
            .put(GBP, 30.0)
            .put(EUR, 20.0)
            .put(JPY, 10.0)
            .put(TWD, 5.0)
            .put(HKD, 5.0)
            .build();
    private final Logger logger = Logger.getGlobal();
    private final List<String> logs = new LinkedList<>();
    private final AccountsApi accountsApi;
    private final RatesApi ratesApi;
    private final QuotesApi quotesApi;
    private final ProfileId profileId;
    private final Map<Currency, Map<Currency, Rate>> rates;

    public RebalanceCurrencies(ProfileId profileId, AccountsApi accountsApi, RatesApi ratesApi,
                               QuotesApi quotesApi) {
        this.accountsApi = accountsApi;
        this.ratesApi = ratesApi;
        this.quotesApi = quotesApi;
        this.profileId = profileId;
        this.rates = getRates();
    }

    @Override
    public List<String> call() {
        Account account = accountsApi.getAccount(profileId);
        log(SEVERE, "Before: " + calculate(account));
        List<Amount> orders = evaluate(account);
        log(INFO, orders.toString());
        convert(account.getId(), orders);
        log(SEVERE, "After : " + calculate(accountsApi.getAccount(profileId)));
        return logs;
    }

    private Amount calculate(Account account) {
        return account.getBalances().stream()
                .map(Balance::getAmount)
                .map(amount -> amount.multiply(rates.get(amount.getCurrency()).get(USD)))
                .reduce(Amount::add)
                .orElse(new Amount(USD, ZERO));
    }

    private Map<Currency, Map<Currency, Rate>> getRates() {
        assert ratesApi != null;
        Map<Currency, Map<Currency, Rate>> map = new HashMap<>();
        map.computeIfAbsent(USD, k -> new HashMap<>()).put(USD, new Rate(USD, USD, ONE));
        for (Rate rate : ratesApi.getTargetRatesNow(USD)) {
            Currency currency = rate.getSource();
            if (currency != null) {
                map.computeIfAbsent(currency, k -> new HashMap<>()).put(USD, rate);
                map.computeIfAbsent(USD, k -> new HashMap<>()).put(currency, rate.reverse());
            }
        }
        return map;
    }

    private Map<Currency, Double> getOptimalPortfolio() {
        List<BalanceCurrency> balanceCurrencies = accountsApi.getBalanceCurrencies();
        log(CONFIG, "IDEAL_PORTFOLIO: " + IDEAL_PORTFOLIO);
        log(CONFIG, "UNWANTED_CURRENCIES: " + UNWANTED_CURRENCIES);
        Map<Currency, Double> optimalPortfolio = IDEAL_PORTFOLIO.entrySet().stream()
                .filter(entry -> !UNWANTED_CURRENCIES.contains(entry.getKey()))
                .filter(entry -> balanceCurrencies.stream().anyMatch(b -> b.getCode().equals(entry.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Sets.SetView<Currency> unsupported = Sets.difference(IDEAL_PORTFOLIO.keySet(), optimalPortfolio.keySet());
        log(INFO, "Ignore unsupported/unwanted currencies: " + unsupported);
        return optimalPortfolio;
    }

    private List<Amount> evaluate(Account account) {
        List<Amount> orders = new LinkedList<>();
        List<Amount> amounts = account.getBalances().stream().map(Balance::getAmount).collect(Collectors.toList());
        if (amounts.isEmpty()) {
            return orders;
        }
        final Map<Currency, Double> optimalPortfolio = getOptimalPortfolio();
        final Double optimalSum = optimalPortfolio.values().stream().reduce(0D, Double::sum);

        Set<Currency> currencies = new HashSet<>();
        currencies.addAll(amounts.stream().map(Amount::getCurrency).collect(Collectors.toList()));
        currencies.addAll(optimalPortfolio.keySet());

        Map<Currency, Amount> existingAmounts = new HashMap<>();
        Map<Currency, Amount> equivalentAmounts = new HashMap<>();
        for (Currency currency : currencies) {
            Amount existingAmount = amounts.stream()
                    .filter(amount -> amount.getCurrency().equals(currency))
                    .findFirst()
                    .orElseGet(() -> new Amount(currency, ZERO));
            existingAmounts.put(currency, existingAmount);
            Amount equivalentAmount = existingAmount.multiply(rates.get(currency).get(USD));
            equivalentAmounts.put(currency, equivalentAmount);
        }

        Amount equivalentSum = equivalentAmounts.values().stream().reduce(new Amount(USD, ZERO), Amount::add);
        List<Currency> sortedCurrencies = currencies.stream()
                .sorted(Comparator.comparing(Currency::getCurrencyCode))
                .collect(Collectors.toList());
        for (Currency currency : sortedCurrencies) {
            Amount existingAmount = existingAmounts.get(currency);
            Amount equivalentAmount = equivalentAmounts.get(currency);
            double existingProportion = equivalentAmount.divide(equivalentSum);
            double optimalProportion = optimalPortfolio.getOrDefault(currency, 0D) / optimalSum;
            Amount optimalAmount = equivalentSum
                    .multiply(new BigDecimal(optimalProportion))
                    .multiply(rates.get(USD).get(currency));
            Amount deviationAmount = optimalAmount.subtract(existingAmount).abs();
            boolean balanced = deviationAmount.getValue().abs().intValue() < 1;
            balanced |= deviationAmount.divide(optimalAmount) < THRESHOLD;
            log(INFO, String.format(
                    "Currency:%s, balanced: %s, equivalent: %s, existing: %s (%.2f%%), optimal: %s (%.2f%%)",
                    currency, balanced, equivalentAmount,
                    existingAmount, existingProportion * 100,
                    optimalAmount, optimalProportion * 100));
            if (!currency.equals(USD) && !balanced) {
                orders.add(optimalAmount.subtract(existingAmount));
            }
        }
        orders.sort(Comparator.comparing(amount -> amount.multiply(rates.get(amount.getCurrency()).get(USD))));
        return orders;
    }

    void convert(AccountId accountId, List<Amount> orders) {
        if (orders.isEmpty()) {
            return;
        }
        CurrencyPairs pairs = accountsApi.getCurrencyPairs();
        for (Amount amount : orders) {
            Currency currency = amount.getCurrency();
            BigDecimal value = amount.getValue().abs().setScale(currency.getDefaultFractionDigits(), HALF_UP);
            BigDecimal maxAmount;
            final BigDecimal minAmount;
            if (amount.isNegative()) {
                // source: currency, target: USD
                maxAmount = pairs.get(currency).map(s -> s.maxInvoiceAmount).orElse(BigDecimal.valueOf(Long.MAX_VALUE));
                minAmount = pairs.get(currency).flatMap(s -> s.get(USD)).map(t -> t.minInvoiceAmount).orElse(ZERO);
            } else {
                // source: USD, target: currency
                maxAmount = pairs.get(USD).map(s -> s.maxInvoiceAmount).orElse(BigDecimal.valueOf(Long.MAX_VALUE));
                minAmount = pairs.get(USD).flatMap(s -> s.get(currency)).map(t -> t.minInvoiceAmount).orElse(ZERO);
            }

            // The most a recipient can get is 480,000 PHP.
            if (currency.equals(PHP)) {
                maxAmount = maxAmount.min(new BigDecimal("480000"));
            }

            if (value.compareTo(maxAmount) > 0) {
                log(WARNING, String.format("Reduce %s from %s to %s", currency, value, maxAmount));
                value = value.min(maxAmount);
            }
            if (value.compareTo(minAmount) < 0) {
                log(WARNING, String.format("Skip %s of %s since it is below %s", currency, value, minAmount));
                continue;
            }
            value = value.setScale(currency.getDefaultFractionDigits(), HALF_UP);
            final Quote quote;
            if (amount.isNegative()) {
                quote = quotesApi.sellSourceToTarget(profileId, currency, value, USD);
            } else if (amount.isPositive()) {
                quote = quotesApi.buyTargetFromSource(profileId, USD, currency, value);
            } else {
                continue;
            }
            if (quote.hasErrors()) {
                log(SEVERE, quote.getErrors().stream().map(Quote.Error::getMessage).collect(Collectors.joining()));
            } else {
                Conversion response = accountsApi.executeQuoteAndConvert(accountId, quote.getId());
                log(INFO, String.format("%s = %s + %s", response.getSourceAmount(), response.getFeeAmount(),
                        response.getTargetAmount()));
            }
        }
    }

    void log(Level level, String msg) {
        logs.add(msg);
        logger.log(level, msg);
    }
}
