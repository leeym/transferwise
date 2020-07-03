package com.leeym.api;

import com.leeym.common.APIToken;
import com.leeym.common.ProfileId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BaseApiITCase {

    protected static final APIToken FAKE_PRODUCTION_API_TOKEN =
            new APIToken("12345678-1234-1234-1234-123456789012", Stage.PRODUCTION);
    protected static final APIToken REAL_SANDBOX_API_TOKEN =
            new APIToken(System.getenv("TRANSFERWISE_SANDBOX_API_TOKEN"), Stage.SANDBOX);
    protected static final ProfileId REAL_SANDBOX_PERSONAL_PROFILE_ID =
            new ProfileId(System.getenv("TRANSFERWISE_SANDBOX_PERSONAL_ID"), Stage.SANDBOX, ProfileId.Type.PERSONAL);
    protected static final ProfileId REAL_SANDBOX_BUSINESS_PROFILE_ID =
            new ProfileId(System.getenv("TRANSFERWISE_SANDBOX_BUSINESS_ID"), Stage.SANDBOX, ProfileId.Type.BUSINESS);

    @Test
    public void testMismatchedStage() {
        Exception e =
                Assertions.assertThrows(IllegalArgumentException.class, () -> new BaseAPI(Stage.PRODUCTION,
                        REAL_SANDBOX_API_TOKEN));
        Assertions.assertEquals("BaseAPI.stage [PRODUCTION] doesn't match APIToken.stage [SANDBOX]", e.getMessage());
    }

    @Test
    public void testNoProductionYet() {
        BaseAPI api = new BaseAPI(Stage.PRODUCTION, FAKE_PRODUCTION_API_TOKEN);
        Exception e = Assertions.assertThrows(UnsupportedOperationException.class, api::getUriPrefix);
        Assertions.assertEquals("Not ready to hit production", e.getMessage());
    }

}
