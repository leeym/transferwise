package com.leeym.operation;

import com.leeym.api.BaseApiITCase;
import com.leeym.api.borderlessaccounts.BorderlessAccountsApi;
import com.leeym.api.exchangerates.RatesApi;
import org.junit.jupiter.api.Test;

import static com.leeym.api.Stage.SANDBOX;

class RebalanceCurrenciesITCase extends BaseApiITCase {

    BorderlessAccountsApi borderlessAccountsAPI = new BorderlessAccountsApi(SANDBOX, REAL_SANDBOX_API_TOKEN);
    RatesApi ratesAPI = new RatesApi(SANDBOX, REAL_SANDBOX_API_TOKEN);

    @Test
    public void testCall() {
        RebalanceCurrencies rebalanceCurrencies =
                new RebalanceCurrencies(borderlessAccountsAPI, ratesAPI, REAL_SANDBOX_PERSONAL_PROFILE_ID);
        rebalanceCurrencies.call();
    }
}
