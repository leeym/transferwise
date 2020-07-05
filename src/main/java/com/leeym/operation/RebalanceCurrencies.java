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
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.leeym.api.Currencies.AED;
import static com.leeym.api.Currencies.AUD;
import static com.leeym.api.Currencies.BRL;
import static com.leeym.api.Currencies.CAD;
import static com.leeym.api.Currencies.CHF;
import static com.leeym.api.Currencies.CLP;
import static com.leeym.api.Currencies.CNY;
import static com.leeym.api.Currencies.COP;
import static com.leeym.api.Currencies.CZK;
import static com.leeym.api.Currencies.DKK;
import static com.leeym.api.Currencies.EUR;
import static com.leeym.api.Currencies.GBP;
import static com.leeym.api.Currencies.HKD;
import static com.leeym.api.Currencies.HUF;
import static com.leeym.api.Currencies.IDR;
import static com.leeym.api.Currencies.ILS;
import static com.leeym.api.Currencies.INR;
import static com.leeym.api.Currencies.JPY;
import static com.leeym.api.Currencies.KRW;
import static com.leeym.api.Currencies.MXN;
import static com.leeym.api.Currencies.MYR;
import static com.leeym.api.Currencies.NOK;
import static com.leeym.api.Currencies.NZD;
import static com.leeym.api.Currencies.PHP;
import static com.leeym.api.Currencies.PLN;
import static com.leeym.api.Currencies.RON;
import static com.leeym.api.Currencies.RUB;
import static com.leeym.api.Currencies.SAR;
import static com.leeym.api.Currencies.SEK;
import static com.leeym.api.Currencies.SGD;
import static com.leeym.api.Currencies.THB;
import static com.leeym.api.Currencies.TRY;
import static com.leeym.api.Currencies.TWD;
import static com.leeym.api.Currencies.USD;
import static com.leeym.api.Currencies.ZAR;
import static com.leeym.api.accounts.Account.Balance;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

/**
 * existing:   the balances currently in the account
 * equivalent: the existing amount calculated in USD
 * ideal:      the portfolio we originally want, though some currencies may be unsupported or unwanted.
 * optimal:    the actual portfolio we can have
 */
public class RebalanceCurrencies implements Callable<List<String>> {

    private static final double THRESHOLD = 0.05;
    private static final Set<Currency> UNWANTED_CURRENCIES = ImmutableSet.of(CNY, HKD);
    // https://en.wikipedia.org/wiki/Template:Most_traded_currencies
    private static final Map<Currency, Double> IDEAL_PORTFOLIO = ImmutableMap.<Currency, Double>builder()
            .put(USD, 88.3) // 1
            .put(EUR, 32.3) // 2
            .put(JPY, 16.8) // 3
            .put(GBP, 12.8) // 4
            .put(AUD, 6.8)  // 5
            .put(CAD, 5.0)  // 6
            .put(CHF, 5.0)  // 7
            .put(CNY, 4.3)  // 8
            .put(HKD, 3.5)  // 9
            .put(NZD, 2.1)  // 10
            .put(SEK, 2.0)  // 11
            .put(KRW, 2.0)  // 12
            .put(SGD, 1.8)  // 13
            .put(NOK, 1.8)  // 14
            .put(MXN, 1.7)  // 15
            .put(INR, 1.7)  // 16
            .put(RUB, 1.1)  // 17
            .put(ZAR, 1.1)  // 18
            .put(TRY, 1.1)  // 19
            .put(BRL, 1.1)  // 20
            .put(TWD, 0.9)  // 21
            .put(DKK, 0.6)  // 22
            .put(PLN, 0.6)  // 23
            .put(THB, 0.5)  // 24
            .put(IDR, 0.4)  // 25
            .put(HUF, 0.4)  // 26
            .put(CZK, 0.4)  // 27
            .put(ILS, 0.3)  // 28
            .put(CLP, 0.3)  // 29
            .put(PHP, 0.3)  // 30
            .put(AED, 0.2)  // 31
            .put(COP, 0.2)  // 32
            .put(SAR, 0.2)  // 33
            .put(MYR, 0.1)  // 34
            .put(RON, 0.1)  // 35
            .build();
    private final Logger logger = Logger.getAnonymousLogger();
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
        List<Amount> amounts = account.getBalances().stream()
                .map(Balance::getAmount)
                .filter(Amount::isPositive)
                .collect(Collectors.toList());
        List<Amount> orders = evaluate(amounts);
        convert(account.getId(), orders);
        return orders.stream().map(Amount::toString).collect(Collectors.toList());
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
        Map<Currency, Double> optimalPortfolio = IDEAL_PORTFOLIO.entrySet().stream()
                .filter(entry -> !UNWANTED_CURRENCIES.contains(entry.getKey()))
                .filter(entry -> balanceCurrencies.stream().anyMatch(b -> b.getCode().equals(entry.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Sets.SetView<Currency> unsupported = Sets.difference(IDEAL_PORTFOLIO.keySet(), optimalPortfolio.keySet());
        logger.info("Ignore unsupported/unwanted currencies: " + unsupported);
        return optimalPortfolio;
    }

    private List<Amount> evaluate(List<Amount> amounts) {
        if (amounts.isEmpty()) {
            return Collections.emptyList();
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
        List<Amount> orders = new LinkedList<>();
        for (Currency currency : currencies) {
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
            logger.info(String.format(
                    "Currency:%s, balanced: %s, equivalent: %s, existing: %s (%.2f%%), optimal: %s (%.2f%%)\n",
                    currency, balanced, equivalentAmount,
                    existingAmount, existingProportion * 100,
                    optimalAmount, optimalProportion * 100));
            if (!currency.equals(USD) && !balanced) {
                orders.add(optimalAmount.subtract(existingAmount));
            }
        }
        logger.info("Account total value: " + equivalentSum);
        return orders;
    }

    void convert(AccountId accountId, List<Amount> orders) {
        if (orders.isEmpty()) {
            return;
        }
        orders.sort(Comparator.comparing(amount -> amount.multiply(rates.get(amount.getCurrency()).get(USD))));
        CurrencyPairs pairs = accountsApi.getCurrencyPairs();
        for (Amount amount : orders) {
            Currency currency = amount.getCurrency();
            BigDecimal value = amount.getValue().abs().setScale(currency.getDefaultFractionDigits(), HALF_UP);
            BigDecimal maxAmount;
            final BigDecimal minAmount;
            if (amount.isNegative()) {
                // source: currency, target: USD
                maxAmount = pairs.get(currency).map(s -> s.maxInvoiceAmount).orElse(ZERO);
                minAmount = pairs.get(currency).flatMap(s -> s.get(USD)).map(t -> t.minInvoiceAmount).orElse(ZERO);
            } else {
                // source: USD, target: currency
                maxAmount = pairs.get(USD).map(s -> s.maxInvoiceAmount).orElse(ZERO);
                minAmount = pairs.get(USD).flatMap(s -> s.get(currency)).map(t -> t.minInvoiceAmount).orElse(ZERO);
            }

            // The most a recipient can get is 480,000 PHP.
            if (currency.equals(PHP)) {
                maxAmount = maxAmount.min(new BigDecimal("480000"));
            }

            if (value.compareTo(maxAmount) > 0) {
                logger.info(String.format("Reduce %s from %s to %s\n", currency, value, maxAmount));
                value = value.min(maxAmount);
            }
            if (value.compareTo(minAmount) < 0) {
                logger.info(String.format("Skip %s of %s since it is below %s\n", currency, value, minAmount));
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
            assert !quote.hasErrors();
            Conversion response = accountsApi.executeQuoteAndConvert(accountId, quote.getId());
            logger.info(String.format("%s = %s + %s", response.getSourceAmount(), response.getFeeAmount(),
                    response.getTargetAmount()));
        }
    }
}
