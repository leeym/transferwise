package com.leeym.operation;

import com.google.common.collect.Iterables;
import com.leeym.api.borderlessaccounts.BorderlessAccount;
import com.leeym.api.borderlessaccounts.BorderlessAccountsApi;
import com.leeym.api.exchangerates.Rate;
import com.leeym.api.exchangerates.RatesApi;
import com.leeym.api.exchangerates.RatesRequest;
import com.leeym.common.Amount;
import com.leeym.common.ProfileId;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static com.leeym.api.Currencies.USD;
import static com.leeym.api.borderlessaccounts.BorderlessAccount.Balance;

public class RebalanceCurrencies implements Callable<String> {

    private final BorderlessAccountsApi borderlessAccountsAPI;
    private final RatesApi ratesAPI;
    private final ProfileId profileId;

    public RebalanceCurrencies(BorderlessAccountsApi borderlessAccountsAPI, RatesApi ratesAPI, ProfileId profileId) {
        this.borderlessAccountsAPI = borderlessAccountsAPI;
        this.ratesAPI = ratesAPI;
        this.profileId = profileId;
    }

    @Override
    public String call() {
        BorderlessAccount account = borderlessAccountsAPI.getBorderlessAccount(profileId);
        List<Amount> amounts = account.getBalances().stream().map(Balance::getAmount).collect(Collectors.toList());
        Map<Amount, Amount> map = new HashMap<>();
        Amount existingUsdEquivalentAmount = new Amount(USD, BigDecimal.ZERO);
        for (Amount sourceAmount : amounts) {
            final Amount targetAmount;
            if (sourceAmount.getCurrency().equals(USD)) {
                targetAmount = sourceAmount;
            } else {
                Currency sourceCurrency = sourceAmount.getCurrency();
                BigDecimal sourceValue = sourceAmount.getValue();
                Rate rate = Iterables.getOnlyElement(ratesAPI.getRates(new RatesRequest(sourceCurrency, USD)));
                BigDecimal targetValue = sourceValue.multiply(rate.getRate());
                targetAmount = new Amount(USD, targetValue);
            }
            map.put(sourceAmount, targetAmount);
            existingUsdEquivalentAmount = existingUsdEquivalentAmount.add(targetAmount);
            System.err.println(Arrays.asList(sourceAmount, targetAmount));
        }
        System.err.println(existingUsdEquivalentAmount);
        return map.toString();
    }
}
