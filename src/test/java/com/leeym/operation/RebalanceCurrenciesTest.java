package com.leeym.operation;

import com.leeym.api.BaseAPITest;
import com.leeym.api.Stage;
import com.leeym.api.borderlessaccounts.BorderlessAccountsAPI;
import com.leeym.api.exchangerates.RatesAPI;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class RebalanceCurrenciesTest extends BaseAPITest {

    BorderlessAccountsAPI borderlessAccountsAPI = new BorderlessAccountsAPI(Stage.SANDBOX, REAL_SANDBOX_API_TOKEN);
    RatesAPI ratesAPI = new RatesAPI(Stage.SANDBOX, REAL_SANDBOX_API_TOKEN);

    @Disabled
    @Test
    public void testCall() {
        RebalanceCurrencies rebalanceCurrencies =
                new RebalanceCurrencies(borderlessAccountsAPI, ratesAPI, REAL_SANDBOX_PERSONAL_PROFILE_ID);
        rebalanceCurrencies.call();
    }
}
