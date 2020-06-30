package com.leeym.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

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
}