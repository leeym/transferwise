package com.leeym.api.accounts;

import com.leeym.api.BaseApiITCase;
import org.junit.jupiter.api.Test;

import static com.leeym.common.BaseUrl.SANDBOX_BASEURL;

class AccountsApiITCase extends BaseApiITCase {
    private final AccountsApi api = new AccountsApi(SANDBOX_BASEURL, SANDBOX_API_TOKEN);

    @Test
    public void getAccount() {
        api.getAccount(SANDBOX_PERSONAL_PROFILE_ID);
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
