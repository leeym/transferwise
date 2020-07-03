package com.leeym.api;

import com.leeym.api.users.UserId;
import com.leeym.common.ApiToken;
import com.leeym.common.ProfileId;
import org.junit.jupiter.api.Test;

import static com.leeym.api.Stage.PRODUCTION;
import static com.leeym.api.Stage.SANDBOX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BaseApiITCase {

    // FAKE
    protected static final ApiToken PRODUCTION_API_TOKEN =
            new ApiToken("12345678-1234-1234-1234-123456789012", PRODUCTION);

    // REAL, from ENV
    protected static final ApiToken SANDBOX_API_TOKEN =
            new ApiToken(System.getenv("TRANSFERWISE_SANDBOX_API_TOKEN"), SANDBOX);
    protected static final ProfileId SANDBOX_PERSONAL_PROFILE_ID =
            new ProfileId(System.getenv("TRANSFERWISE_SANDBOX_PERSONAL_ID"));
    protected static final ProfileId SANDBOX_BUSINESS_PROFILE_ID =
            new ProfileId(System.getenv("TRANSFERWISE_SANDBOX_BUSINESS_ID"));
    protected static final UserId SANDBOX_USER_ID = new UserId(System.getenv("TRANSFERWISE_SANDBOX_USER_ID"));

    @Test
    public void testMismatchedStage() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new BaseApi(PRODUCTION, SANDBOX_API_TOKEN));
        assertEquals("BaseApi.stage [PRODUCTION] doesn't match ApiToken.stage [SANDBOX]", e.getMessage());
    }

    @Test
    public void testNoProductionYet() {
        BaseApi api = new BaseApi(PRODUCTION, PRODUCTION_API_TOKEN);
        Exception e = assertThrows(UnsupportedOperationException.class, api::getUriPrefix);
        assertEquals("Not ready to hit production", e.getMessage());
    }

}
