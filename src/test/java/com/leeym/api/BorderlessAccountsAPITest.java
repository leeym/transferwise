package com.leeym.api;

import com.leeym.api.borderlessaccounts.BorderlessAccount;
import com.leeym.api.borderlessaccounts.BorderlessAccountsAPI;
import com.leeym.api.borderlessaccounts.CurrencyPairs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

class BorderlessAccountsAPITest extends BaseAPITest {
    private final BorderlessAccountsAPI api = new BorderlessAccountsAPI(Stage.SANDBOX, REAL_SANDBOX_API_TOKEN);

    @Disabled
    @Test
    public void testBorderlessAccounts() {
        List<BorderlessAccount> borderlessAccounts = api.getBorderlessAccounts(REAL_SANDBOX_PERSONAL_PROFILE_ID);
        Assertions.assertEquals(1, borderlessAccounts.size());
        BorderlessAccount borderlessAccount = borderlessAccounts.get(0);
        System.err.println(borderlessAccount);
    }

    @Disabled
    @Test
    public void testCurrencyPairs() {
        CurrencyPairs currencyPairs = api.getCurrencyPairs();
        Map<String, Set<String>> map = new HashMap<>();
        for (CurrencyPairs.SourceCurrency sourceCurrency : currencyPairs.sourceCurrencies) {
            for (CurrencyPairs.TargetCurrency targetCurrency : sourceCurrency.targetCurrencies) {
                map.computeIfAbsent(sourceCurrency.currencyCode, k->new HashSet<>()).add(targetCurrency.currencyCode);
            }
        }
        System.err.println(map);
    }
}