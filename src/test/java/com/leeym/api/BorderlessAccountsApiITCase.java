package com.leeym.api;

import com.leeym.api.borderlessaccounts.BorderlessAccount;
import com.leeym.api.borderlessaccounts.BorderlessAccountsApi;
import org.junit.jupiter.api.Test;

import static com.leeym.api.Stage.SANDBOX;

class BorderlessAccountsApiITCase extends BaseApiITCase {
    private final BorderlessAccountsApi api = new BorderlessAccountsApi(SANDBOX, REAL_SANDBOX_API_TOKEN);

    @Test
    public void testBorderlessAccounts() {
        BorderlessAccount borderlessAccount = api.getBorderlessAccount(REAL_SANDBOX_PERSONAL_PROFILE_ID);
        System.err.println(borderlessAccount);
    }
}
