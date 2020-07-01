package com.leeym.operation;

import com.leeym.api.*;
import com.leeym.common.CurrencyCode;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

class RebalanceTest extends BaseAPITest {

    BorderlessAccountsAPI borderlessAccountsAPI = new BorderlessAccountsAPI(Stage.SANDBOX, REAL_SANDBOX_API_TOKEN);
    RatesAPI ratesAPI = new RatesAPI(Stage.SANDBOX, REAL_SANDBOX_API_TOKEN);

    @Test
    public void test1() {
        List<BorderlessAccount> borderlessAccounts = borderlessAccountsAPI.getBorderlessAccounts(REAL_SANDBOX_PERSONAL_PROFILE_ID);
        BorderlessAccount borderlessAccount = borderlessAccounts.get(0);
        Map<String, BigDecimal> rateToUSD = new TreeMap<>();
        ratesAPI.getRates(new CurrencyCode("USD")).forEach(rate -> {
            rateToUSD.put(rate.getTarget(), rate.getRate());
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
        }
        System.err.println(map);
    }
}