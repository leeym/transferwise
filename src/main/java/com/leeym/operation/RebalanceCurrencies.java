package com.leeym.operation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.leeym.api.borderlessaccounts.Account;
import com.leeym.api.borderlessaccounts.AccountId;
import com.leeym.api.borderlessaccounts.AccountsApi;
import com.leeym.api.borderlessaccounts.BalanceCurrency;
import com.leeym.api.borderlessaccounts.ConversionResponse;
import com.leeym.api.borderlessaccounts.CurrencyPairs;
import com.leeym.api.exchangerates.Rate;
import com.leeym.api.exchangerates.RatesApi;
import com.leeym.api.quotes.QuoteId;
import com.leeym.api.quotes.QuotesApi;
import com.leeym.api.userprofiles.ProfileId;
import com.leeym.common.Amount;

import java.math.BigDecimal;
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

import static com.leeym.api.Currencies.AUD;
import static com.leeym.api.Currencies.BRL;
import static com.leeym.api.Currencies.CAD;
import static com.leeym.api.Currencies.CHF;
import static com.leeym.api.Currencies.CNY;
import static com.leeym.api.Currencies.EUR;
import static com.leeym.api.Currencies.GBP;
import static com.leeym.api.Currencies.HKD;
import static com.leeym.api.Currencies.INR;
import static com.leeym.api.Currencies.JPY;
import static com.leeym.api.Currencies.KRW;
import static com.leeym.api.Currencies.MXN;
import static com.leeym.api.Currencies.NOK;
import static com.leeym.api.Currencies.NZD;
import static com.leeym.api.Currencies.RUB;
import static com.leeym.api.Currencies.SEK;
import static com.leeym.api.Currencies.SGD;
import static com.leeym.api.Currencies.TRY;
import static com.leeym.api.Currencies.TWD;
import static com.leeym.api.Currencies.USD;
import static com.leeym.api.Currencies.ZAR;
import static com.leeym.api.borderlessaccounts.Account.Balance;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

/**
 * existing:  the balances currently in the account
 * optimal:   the portfolio we want, though some currencies may not be supported.
 * effective: the actual one we can have
 */
public class RebalanceCurrencies implements Callable<String> {

    private final Logger logger = Logger.getAnonymousLogger();

    // https://en.wikipedia.org/wiki/Template:Most_traded_currencies
    private final Map<Currency, Double> optimalAllocation = ImmutableMap.<Currency, Double>builder()
            .put(USD, 88.3)
            .put(EUR, 32.3)
            .put(JPY, 16.8)
            .put(GBP, 12.8)
            .put(AUD, 6.8)
            .put(CAD, 5.0)
            .put(CHF, 5.0)
            .put(CNY, 4.3)
            .put(HKD, 3.5)
            .put(NZD, 2.1)
            .put(SEK, 2.0)
            .put(KRW, 2.0)
            .put(SGD, 1.8)
            .put(NOK, 1.8)
            .put(MXN, 1.7)
            .put(INR, 1.7)
            .put(RUB, 1.1)
            .put(ZAR, 1.1)
            .put(TRY, 1.1)
            .put(BRL, 1.1)
            .put(TWD, 0.9)
            .build();

    private final AccountsApi accountsApi;
    private final RatesApi ratesApi;
    private final QuotesApi quotesApi;
    private final ProfileId profileId;

    public RebalanceCurrencies(ProfileId profileId, AccountsApi accountsApi, RatesApi ratesApi,
                               QuotesApi quotesApi) {
        this.accountsApi = accountsApi;
        this.ratesApi = ratesApi;
        this.quotesApi = quotesApi;
        this.profileId = profileId;
    }

    @Override
    public String call() {
        // see which currencies the account can hold, and ignore the unsupported ones.
        // these allocations will be reallocated to the default currency, which is USD.
        List<BalanceCurrency> balanceCurrencies = accountsApi.getBalanceCurrencies();
        final Map<Currency, Double> effectiveAllocation = optimalAllocation.entrySet().stream()
                .filter(entry -> balanceCurrencies.stream().anyMatch(b -> b.getCode().equals(entry.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Sets.SetView<Currency> unsupported = Sets.difference(optimalAllocation.keySet(), effectiveAllocation.keySet());
        logger.info("Ignore unsupported currencies: " + unsupported);
        final Double effectiveSum = effectiveAllocation.values().stream().reduce(0D, Double::sum);

        Account account = accountsApi.getAccount(profileId);
        AccountId accountId = account.getId();
        List<Amount> amounts = account.getBalances().stream().map(Balance::getAmount).collect(Collectors.toList());
        Set<Currency> currencies = new HashSet<>();
        currencies.addAll(effectiveAllocation.keySet());
        currencies.addAll(amounts.stream().map(Amount::getCurrency).collect(Collectors.toList()));
        Map<Currency, Amount> existingAmounts = new HashMap<>();
        Map<Currency, Amount> equivalentAmounts = new HashMap<>();
        Map<Currency, Map<Currency, Double>> rates = new HashMap<>();
        for (Currency currency : currencies) {
            Amount existingAmount = amounts.stream()
                    .filter(amount -> amount.getCurrency().equals(currency))
                    .findFirst()
                    .orElseGet(() -> new Amount(currency, ZERO));
            existingAmounts.put(currency, existingAmount);
            Rate rate = ratesApi.getRateNow(currency, USD);
            double doubleValue = rate.getRate().doubleValue();
            rates.computeIfAbsent(currency, k -> new HashMap<>()).put(USD, doubleValue);
            rates.computeIfAbsent(USD, k -> new HashMap<>()).put(currency, 1 / doubleValue);
            Amount equivalentAmount = new Amount(USD, existingAmount.getValue().multiply(rate.getRate()));
            equivalentAmounts.put(currency, equivalentAmount);
        }
        Amount equivalentSum = equivalentAmounts.values().stream().reduce(new Amount(USD, ZERO), Amount::add);
        List<Amount> orders = new LinkedList<>();
        for (Currency currency : currencies) {
            Amount existingAmount = existingAmounts.get(currency);
            Amount equivalentAmount = equivalentAmounts.get(currency);
            double existingPercentage =
                    equivalentAmount.getValue().doubleValue() * 100 / equivalentSum.getValue().doubleValue();
            double optimalPercentage = optimalAllocation.getOrDefault(currency, 0D) * 100 / effectiveSum;
            Amount optimalAmount = new Amount(currency, equivalentSum.getValue()
                    .multiply(new BigDecimal(optimalPercentage))
                    .multiply(BigDecimal.valueOf(rates.get(USD).get(currency))));
            boolean balanced = existingPercentage > 0 && Math.abs(optimalPercentage - existingPercentage) < 1;
            logger.info(String.format("Currency:%s, balanced: %s, existing: %s (%.2f%%), optimal: %s (%.2f%%)\n",
                    currency, balanced, existingAmount, existingPercentage, optimalAmount, optimalPercentage));
            if (!currency.equals(USD) && !balanced) {
                orders.add(optimalAmount.subtract(existingAmount));
            }
        }
        if (orders.isEmpty()) {
            return orders.toString();
        }
        orders.sort((o1, o2) -> {
            double d1 = o1.getValue().doubleValue() * rates.get(o1.getCurrency()).get(USD);
            double d2 = o2.getValue().doubleValue() * rates.get(o2.getCurrency()).get(USD);
            return Double.compare(d1, d2);
        });
        CurrencyPairs pairs = accountsApi.getCurrencyPairs();
        for (Amount amount : orders) {
            Currency currency = amount.getCurrency();
            BigDecimal value = amount.getValue().abs().setScale(currency.getDefaultFractionDigits(), HALF_UP);
            final BigDecimal maxAmount;
            final BigDecimal minAmount;
            if (amount.isNegative()) {
                // source: currency, target: USD
                maxAmount = pairs.get(currency).map(s -> s.maxInvoiceAmount).orElse(ZERO);
                minAmount = pairs.get(currency).flatMap(s -> s.get(USD)).map(t -> t.minInvoiceAmount).orElse(ZERO);
            } else {
                // source: USD, target: currency
                maxAmount = pairs.get(USD).map(c -> c.maxInvoiceAmount).orElse(ZERO);
                minAmount = pairs.get(USD).flatMap(s -> s.get(currency)).map(t -> t.minInvoiceAmount).orElse(ZERO);
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
            final QuoteId quoteId;
            if (amount.isNegative()) {
                quoteId = quotesApi.sellSourceToTarget(profileId, currency, value, USD).getId();
            } else if (amount.isPositive()) {
                quoteId = quotesApi.buyTargetFromSource(profileId, USD, currency, value).getId();
            } else {
                continue;
            }
            assert !quoteId.toString().isEmpty();
            ConversionResponse response = accountsApi.executeQuoteAndConvert(accountId, quoteId);
        }
        return orders.toString();
    }
}
