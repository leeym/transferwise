package com.leeym.operation;

import com.google.common.collect.ImmutableMap;
import com.leeym.api.borderlessaccounts.Account;
import com.leeym.api.borderlessaccounts.AccountId;
import com.leeym.api.borderlessaccounts.AccountsApi;
import com.leeym.api.borderlessaccounts.ConversionResponse;
import com.leeym.api.borderlessaccounts.CurrencyPairs;
import com.leeym.api.exchangerates.Rate;
import com.leeym.api.exchangerates.RatesApi;
import com.leeym.api.quotes.QuoteId;
import com.leeym.api.quotes.QuotesApi;
import com.leeym.api.userprofiles.ProfileId;
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
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.leeym.api.Currencies.AUD;
import static com.leeym.api.Currencies.EUR;
import static com.leeym.api.Currencies.GBP;
import static com.leeym.api.Currencies.JPY;
import static com.leeym.api.Currencies.USD;
import static com.leeym.api.borderlessaccounts.Account.Balance;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

public class RebalanceCurrencies implements Callable<String> {

    private static final Currency DEFAULT = USD;
    private final Logger logger = Logger.getAnonymousLogger();

    // https://en.wikipedia.org/wiki/Template:Most_traded_currencies
    private final Map<Currency, Double> idealAllocation = ImmutableMap.<Currency, Double>builder()
            .put(USD, 88.3)
            .put(EUR, 32.3)
            .put(JPY, 16.8)
            .put(GBP, 12.8)
            .put(AUD, 6.8)
            .build();

    private final Double idealSum = idealAllocation.values().stream().reduce(0D, Double::sum);

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
        Account account = accountsApi.getAccount(profileId);
        AccountId accountId = account.getId();
        List<Amount> amounts = account.getBalances().stream().map(Balance::getAmount).collect(Collectors.toList());
        Set<Currency> currencies = new HashSet<>();
        currencies.addAll(idealAllocation.keySet());
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
            Rate rate = ratesApi.getRateNow(currency, DEFAULT);
            double doubleValue = rate.getRate().doubleValue();
            rates.computeIfAbsent(currency, k -> new HashMap<>()).put(DEFAULT, doubleValue);
            rates.computeIfAbsent(DEFAULT, k -> new HashMap<>()).put(currency, 1 / doubleValue);
            Amount equivalentAmount = new Amount(DEFAULT, existingAmount.getValue().multiply(rate.getRate()));
            equivalentAmounts.put(currency, equivalentAmount);
        }
        Amount equivalentSum = equivalentAmounts.values().stream().reduce(new Amount(DEFAULT, ZERO), Amount::add);
        List<Amount> orders = new LinkedList<>();
        for (Currency currency : currencies) {
            Amount existingAmount = existingAmounts.get(currency);
            Amount equivalentAmount = equivalentAmounts.get(currency);
            double existingPercentage =
                    equivalentAmount.getValue().doubleValue() * 100 / equivalentSum.getValue().doubleValue();
            double idealPercentage = idealAllocation.getOrDefault(currency, 0D) * 100 / idealSum;
            Amount idealAmount = new Amount(currency, equivalentSum.getValue()
                    .multiply(new BigDecimal(idealPercentage))
                    .multiply(BigDecimal.valueOf(rates.get(DEFAULT).get(currency))));
            boolean balanced = Math.abs(idealPercentage - existingPercentage) < 1;
            logger.info(String.format("Currency:%s, balanced: %s, existing: %s (%.2f%%), ideal: %s (%.2f%%)\n",
                    currency, balanced, existingAmount, existingPercentage, idealAmount, idealPercentage));
            if (balanced) {
                continue;
            }
            logger.info(String.format("Rebalancing %s from %.2f%% to %.2f%%\n", currency, existingPercentage,
                    idealPercentage));
            orders.add(idealAmount.subtract(existingAmount));
        }
        if (orders.isEmpty()) {
            return orders.toString();
        }
        orders.sort(Comparator.comparing(Amount::getValue));
        CurrencyPairs currencyPairs = accountsApi.getCurrencyPairs();
        for (Amount amount : orders) {
            Currency currency = amount.getCurrency();
            BigDecimal value = amount.getValue().abs();
            final BigDecimal maxInvoiceAmount;
            final BigDecimal minInvoiceAmount;
            if (amount.isNegative()) {
                // source: currency, target: DEFAULT
                maxInvoiceAmount = currencyPairs.get(currency).maxInvoiceAmount;
                minInvoiceAmount = currencyPairs.get(currency).get(DEFAULT).minInvoiceAmount;
            } else {
                // source: DEFAULT, target: currency
                maxInvoiceAmount = currencyPairs.get(DEFAULT).maxInvoiceAmount;
                minInvoiceAmount = currencyPairs.get(DEFAULT).get(currency).minInvoiceAmount;
            }
            if (value.compareTo(maxInvoiceAmount) > 0) {
                logger.info(String.format("Reduce %s from %s to %s\n", currency, value, maxInvoiceAmount));
                value = value.min(maxInvoiceAmount);
            }
            if (value.compareTo(minInvoiceAmount) < 0) {
                logger.info(String.format("Skip %s of %s since it is below %s\n", currency, value, minInvoiceAmount));
                continue;
            }
            value = value.setScale(currency.getDefaultFractionDigits(), HALF_UP);
            final QuoteId quoteId;
            if (amount.isNegative()) {
                quoteId = quotesApi.sellSourceToTarget(profileId, currency, value, DEFAULT).getId();
            } else if (amount.isPositive()) {
                quoteId = quotesApi.buyTargetFromSource(profileId, DEFAULT, currency, value).getId();
            } else {
                continue;
            }
            assert !quoteId.toString().isEmpty();
            ConversionResponse response = accountsApi.executeQuoteAndConvert(accountId, quoteId);
        }
        return orders.toString();
    }
}
