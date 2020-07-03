package com.leeym.api;

import com.leeym.api.borderlessaccounts.BorderlessAccount;
import com.leeym.api.borderlessaccounts.BorderlessAccountsAPI;
import com.leeym.api.borderlessaccounts.CurrencyPairs;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class BorderlessAccountsApiITCase extends BaseApiITCase {
    private final BorderlessAccountsAPI api = new BorderlessAccountsAPI(Stage.SANDBOX, REAL_SANDBOX_API_TOKEN);

    @Test
    public void testBorderlessAccounts() {
        BorderlessAccount borderlessAccount = api.getBorderlessAccount(REAL_SANDBOX_PERSONAL_PROFILE_ID);
        System.err.println(borderlessAccount);
    }

    @Test
    public void testCurrencyPairs() {
        CurrencyPairs currencyPairs = api.getCurrencyPairs();
        Map<String, Set<String>> map = new HashMap<>();
        for (CurrencyPairs.SourceCurrency sourceCurrency : currencyPairs.sourceCurrencies) {
            for (CurrencyPairs.TargetCurrency targetCurrency : sourceCurrency.targetCurrencies) {
                map.computeIfAbsent(sourceCurrency.currencyCode, k -> new HashSet<>()).add(targetCurrency.currencyCode);
            }
        }
        System.err.println(map);
    }
}
