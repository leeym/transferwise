package com.leeym.operation;

import com.google.common.collect.ImmutableMap;
import com.leeym.api.borderlessaccounts.BorderlessAccount;
import com.leeym.api.borderlessaccounts.BorderlessAccountsApi;
import com.leeym.api.exchangerates.Rate;
import com.leeym.api.exchangerates.RatesApi;
import com.leeym.api.quotes.QuoteResponse;
import com.leeym.api.quotes.QuotesApi;
import com.leeym.api.userprofiles.ProfileId;
import com.leeym.common.Amount;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static com.leeym.api.Currencies.EUR;
import static com.leeym.api.Currencies.GBP;
import static com.leeym.api.Currencies.USD;
import static com.leeym.api.borderlessaccounts.BorderlessAccount.Balance;

public class RebalanceCurrencies implements Callable<String> {

    private final Map<Currency, Integer> idealAllocation = ImmutableMap.of(
            USD, 40,
            GBP, 30,
            EUR, 30
    );

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
        BorderlessAccount account = borderlessAccountsApi.getBorderlessAccount(profileId);
        List<Amount> amounts = account.getBalances().stream().map(Balance::getAmount).collect(Collectors.toList());
        Map<Currency, List<Amount>> existingAllication = new HashMap<>();
        for (Amount sourceAmount : amounts) {
            final Amount targetAmount;
            if (sourceAmount.getCurrency().equals(USD)) {
                targetAmount = sourceAmount;
            } else {
                Currency sourceCurrency = sourceAmount.getCurrency();
                BigDecimal sourceValue = sourceAmount.getValue();
                Rate rate = ratesApi.getRateNow(sourceCurrency, USD);
                BigDecimal targetValue = sourceValue.multiply(rate.getRate());
                targetAmount = new Amount(USD, targetValue);
            }
            existingAllication.put(sourceAmount.getCurrency(), Arrays.asList(sourceAmount, targetAmount));
        }
        System.err.println(existingAllication);

        // sell the unwanted currencies
        for (Currency currency : existingAllication.keySet()) {
            if (!idealAllocation.containsKey(currency)) {
                Amount amount = existingAllication.get(currency).get(0);
                QuoteResponse response = quotesApi.sellSourceToTarget(profileId, currency, amount.getValue(), USD);
                System.err.println(response);
            }
        }

        return existingAllication.toString();
    }
}
