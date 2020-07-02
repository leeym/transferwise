package com.leeym.operation;

import com.leeym.api.*;
import com.leeym.common.CurrencyCode;
import com.leeym.common.ProfileId;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class Rebalance implements Callable<String> {

    private final BorderlessAccountsAPI borderlessAccountsAPI;
    private final RatesAPI ratesAPI;
    private final ProfileId profileId;

    public Rebalance(BorderlessAccountsAPI borderlessAccountsAPI, RatesAPI ratesAPI, ProfileId profileId) {
        this.borderlessAccountsAPI = borderlessAccountsAPI;
        this.ratesAPI = ratesAPI;
        this.profileId = profileId;
    }

    @Override
    public String call() {
        List<BorderlessAccount> borderlessAccounts = borderlessAccountsAPI.getBorderlessAccounts(profileId);
        BorderlessAccount borderlessAccount = borderlessAccounts.get(0);
        Map<String, BigDecimal> rateToUSD = new TreeMap<>();
        ratesAPI.getRates(new CurrencyCode("USD")).forEach(rate -> {
            rateToUSD.put(rate.getSource(), rate.getRate());
        });
        List<Amount> amounts = borderlessAccount.getBalances().stream().map(Balance::getAmount).collect(Collectors.toList());
        Map<Amount, Amount> map = new HashMap<>();
        for (Amount sourceAmount : amounts) {
            if (sourceAmount.getCurrency().equals("USD")) {
                map.put(sourceAmount, sourceAmount);
            } else {
                BigDecimal sourceValue = sourceAmount.getValue();
                BigDecimal rate = rateToUSD.get(sourceAmount.getCurrency());
                BigDecimal targetValue = sourceValue.multiply(rate);
                Amount targetAmount = new Amount("USD", targetValue);
                map.put(sourceAmount, targetAmount);
            }
            System.err.println(Arrays.asList(sourceAmount, map.get(sourceAmount)));
        }
        return map.toString();
    }
}
