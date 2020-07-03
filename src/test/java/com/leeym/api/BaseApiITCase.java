package com.leeym.api;

import com.leeym.common.ApiToken;
import com.leeym.common.ProfileId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BaseApiITCase {

    protected static final ApiToken FAKE_PRODUCTION_API_TOKEN =
            new ApiToken("12345678-1234-1234-1234-123456789012", Stage.PRODUCTION);
    protected static final ApiToken REAL_SANDBOX_API_TOKEN =
            new ApiToken(System.getenv("TRANSFERWISE_SANDBOX_API_TOKEN"), Stage.SANDBOX);
    protected static final ProfileId REAL_SANDBOX_PERSONAL_PROFILE_ID =
            new ProfileId(System.getenv("TRANSFERWISE_SANDBOX_PERSONAL_ID"));
    protected static final ProfileId REAL_SANDBOX_BUSINESS_PROFILE_ID =
            new ProfileId(System.getenv("TRANSFERWISE_SANDBOX_BUSINESS_ID"));

    @Test
    public void testMismatchedStage() {
        Exception e =
                Assertions.assertThrows(IllegalArgumentException.class, () -> new BaseApi(Stage.PRODUCTION,
                        REAL_SANDBOX_API_TOKEN));
        Assertions.assertEquals("BaseApi.stage [PRODUCTION] doesn't match ApiToken.stage [SANDBOX]", e.getMessage());
    }

    @Test
    public void testNoProductionYet() {
        BaseApi api = new BaseApi(Stage.PRODUCTION, FAKE_PRODUCTION_API_TOKEN);
        Exception e = Assertions.assertThrows(UnsupportedOperationException.class, api::getUriPrefix);
        Assertions.assertEquals("Not ready to hit production", e.getMessage());
    }

}
