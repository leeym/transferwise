package com.leeym.api;

import com.leeym.api.borderlessaccounts.BorderlessAccountsApi;
import org.junit.jupiter.api.Test;

import static com.leeym.common.BaseUrl.SANDBOX_BASEURL;

class BorderlessAccountsApiITCase extends BaseApiITCase {
    private final BorderlessAccountsApi api = new BorderlessAccountsApi(SANDBOX_BASEURL, SANDBOX_API_TOKEN);

    @Test
    public void getBorderlessAccount() {
        api.getBorderlessAccount(SANDBOX_PERSONAL_PROFILE_ID);
    }

    @Test
    public void getCurrencyPairs() {
        api.getCurrencyPairs();
    }

    @Test
    public void getBalanceCurrencies() {
        api.getBalanceCurrencies();
    }
}
