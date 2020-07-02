package com.leeym.operation;

import com.leeym.api.BaseAPITest;
import com.leeym.api.borderlessaccounts.BorderlessAccountsAPI;
import com.leeym.api.exchangerates.RatesAPI;
import com.leeym.api.Stage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class RebalanceTest extends BaseAPITest {

    BorderlessAccountsAPI borderlessAccountsAPI = new BorderlessAccountsAPI(Stage.SANDBOX, REAL_SANDBOX_API_TOKEN);
    RatesAPI ratesAPI = new RatesAPI(Stage.SANDBOX, REAL_SANDBOX_API_TOKEN);

    @Disabled
    @Test
    public void testCall() {
        Rebalance rebalance = new Rebalance(borderlessAccountsAPI, ratesAPI, REAL_SANDBOX_PERSONAL_PROFILE_ID);
        rebalance.call();
    }
}