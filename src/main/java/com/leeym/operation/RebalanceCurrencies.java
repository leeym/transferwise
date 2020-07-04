package com.leeym.operation;

import com.google.common.collect.ImmutableMap;
import com.leeym.api.borderlessaccounts.BorderlessAccount;
import com.leeym.api.borderlessaccounts.BorderlessAccountId;
import com.leeym.api.borderlessaccounts.BorderlessAccountsApi;
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
import java.util.stream.Collectors;

import static com.leeym.api.Currencies.AUD;
import static com.leeym.api.Currencies.EUR;
import static com.leeym.api.Currencies.GBP;
import static com.leeym.api.Currencies.JPY;
import static com.leeym.api.Currencies.USD;
import static com.leeym.api.borderlessaccounts.BorderlessAccount.Balance;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

public class RebalanceCurrencies implements Callable<String> {

    // https://en.wikipedia.org/wiki/Template:Most_traded_currencies
    private final Map<Currency, Double> idealAllocation = ImmutableMap.<Currency, Double>builder()
            .put(USD, 88.3)
            .put(EUR, 32.3)
            .put(JPY, 16.8)
            .put(GBP, 12.8)
            .put(AUD, 6.8)
            .build();

    private final Double idealSum = idealAllocation.values().stream().reduce(0D, Double::sum);

    private final BorderlessAccountsApi borderlessAccountsApi;
    private final RatesApi ratesApi;
    private final QuotesApi quotesApi;
    private final ProfileId profileId;

    public RebalanceCurrencies(BorderlessAccountsApi borderlessAccountsApi, RatesApi ratesApi, QuotesApi quotesApi,
                               ProfileId profileId) {
        this.borderlessAccountsApi = borderlessAccountsApi;
        this.ratesApi = ratesApi;
        this.quotesApi = quotesApi;
        this.profileId = profileId;
    }

    @Override
    public String call() {
        CurrencyPairs currencyPairs = borderlessAccountsApi.getCurrencyPairs();
        BorderlessAccount account = borderlessAccountsApi.getBorderlessAccount(profileId);
        BorderlessAccountId accountId = account.getId();
        List<Amount> amounts = account.getBalances().stream().map(Balance::getAmount).collect(Collectors.toList());
        Set<Currency> currencies = new HashSet<>();
        currencies.addAll(idealAllocation.keySet());
        currencies.addAll(amounts.stream().map(Amount::getCurrency).collect(Collectors.toList()));
        Map<Currency, Amount> existingAmounts = new HashMap<>();
        Map<Currency, Amount> equivalentAmounts = new HashMap<>();
        Map<Currency, Map<Currency, Double>> rates = new HashMap<>();
        for (Currency currency : currencies) {
            Amount existingAmount = amounts.stream().filter(amount -> amount.getCurrency().equals(currency))
                    .findFirst().orElseGet(() -> new Amount(currency, ZERO));
            existingAmounts.put(currency, existingAmount);
            Rate rate = ratesApi.getRateNow(currency, USD);
            double doubleValue = rate.getRate().doubleValue();
            rates.computeIfAbsent(currency, k -> new HashMap<>()).put(USD, doubleValue);
            rates.computeIfAbsent(USD, k -> new HashMap<>()).put(currency, 1 / doubleValue);
            Amount equivalentAmount = new Amount(USD, existingAmount.getValue().multiply(rate.getRate()));
            equivalentAmounts.put(currency, equivalentAmount);
        }
        Amount equivalentSum = equivalentAmounts.values().stream().reduce(new Amount(USD, ZERO), Amount::add);
        List<Amount> differences = new LinkedList<>();
        for (Currency currency : currencies) {
            Amount existingAmount = existingAmounts.get(currency);
            Amount equivalentAmount = equivalentAmounts.get(currency);
            double existingPercentage =
                    equivalentAmount.getValue().doubleValue() / equivalentSum.getValue().doubleValue();
            double idealPercentage = idealAllocation.getOrDefault(currency, 0D) / idealSum;
            Amount idealAmount = new Amount(currency, equivalentSum.getValue()
                    .multiply(new BigDecimal(idealPercentage))
                    .multiply(BigDecimal.valueOf(rates.get(USD).get(currency))));
            System.err.printf("Currency:%s, existing: %s %.2f%%, ideal: %s %.2f%%\n", currency,
                    existingAmount, existingPercentage * 100, idealAmount, idealPercentage * 100);
            if (!currency.equals(USD)) {
                differences.add(idealAmount.subtract(existingAmount));
            }
        }
        differences.sort(Comparator.comparing(Amount::getValue));
        System.err.println(differences);
        for (Amount amount : differences) {
            Currency currency = amount.getCurrency();
            BigDecimal value = amount.getValue().abs();
            BigDecimal maxInvoiceAmount = currencyPairs.getSourceCurrency(currency).maxInvoiceAmount;
            value = value.min(maxInvoiceAmount).setScale(currency.getDefaultFractionDigits(), HALF_UP);
            final QuoteId quoteId;
            if (amount.isNegative()) {
                quoteId = quotesApi.sellSourceToTarget(profileId, currency, value, USD).getId();
            } else if (amount.isPositive()) {
                quoteId = quotesApi.buyTargetFromSource(profileId, USD, currency, value).getId();
            } else {
                continue;
            }
            assert !quoteId.toString().isEmpty();
            ConversionResponse response = borderlessAccountsApi.executeQuoteAndConvert(accountId, quoteId);
            System.err.println(response);
        }
        return "";
    }
}
