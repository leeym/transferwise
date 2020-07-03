package com.leeym.operation;

import com.leeym.api.borderlessaccounts.Amount;
import com.leeym.api.borderlessaccounts.Balance;
import com.leeym.api.borderlessaccounts.BorderlessAccount;
import com.leeym.api.borderlessaccounts.BorderlessAccountsAPI;
import com.leeym.api.exchangerates.Rate;
import com.leeym.api.exchangerates.RatesAPI;
import com.leeym.api.exchangerates.RatesRequest;
import com.leeym.common.ProfileId;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class RebalanceCurrencies implements Callable<String> {

    private final BorderlessAccountsAPI borderlessAccountsAPI;
    private final RatesAPI ratesAPI;
    private final ProfileId profileId;

    public RebalanceCurrencies(BorderlessAccountsAPI borderlessAccountsAPI, RatesAPI ratesAPI, ProfileId profileId) {
        this.borderlessAccountsAPI = borderlessAccountsAPI;
        this.ratesAPI = ratesAPI;
        this.profileId = profileId;
    }

    @Override
    public String call() {
        BorderlessAccount account = borderlessAccountsAPI.getBorderlessAccount(profileId);
        List<Amount> amounts = account.getBalances().stream().map(Balance::getAmount).collect(Collectors.toList());
        Map<Amount, Amount> map = new HashMap<>();
        for (Amount sourceAmount : amounts) {
            if (sourceAmount.getCurrency().equals(Currency.getInstance("USD"))) {
                map.put(sourceAmount, sourceAmount);
            } else {
                Currency sourceCurrency = sourceAmount.getCurrency();
                Currency targetCurrency = Currency.getInstance("USD");
                BigDecimal sourceValue = sourceAmount.getValue();
                Rate rate = ratesAPI.getRates(new RatesRequest(sourceCurrency, targetCurrency)).get(0);
                BigDecimal targetValue = sourceValue.multiply(rate.getRate());
                Amount targetAmount = new Amount(Currency.getInstance("USD"), targetValue);
                map.put(sourceAmount, targetAmount);
            }
            System.err.println(Arrays.asList(sourceAmount, map.get(sourceAmount)));
        }
        return map.toString();
    }
}
