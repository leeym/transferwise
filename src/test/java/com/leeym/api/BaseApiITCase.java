package com.leeym.api;

import com.leeym.api.users.UserId;
import com.leeym.common.ApiToken;
import com.leeym.api.userprofiles.ProfileId;
import org.junit.jupiter.api.Test;

import static com.leeym.common.BaseUrl.LIVE_BASEURL;
import static com.leeym.common.BaseUrl.SANDBOX_BASEURL;
import static com.leeym.common.Stage.LIVE;
import static com.leeym.common.Stage.SANDBOX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BaseApiITCase {

    // FAKE
    protected static final ApiToken LIVE_API_TOKEN = new ApiToken("ApiToken-Fake-Live-Test-TransferWise", LIVE);

    // REAL, from ENV
    protected static final ApiToken SANDBOX_API_TOKEN =
            new ApiToken(System.getenv("TRANSFERWISE_SANDBOX_API_TOKEN"), SANDBOX);
    protected static final ProfileId SANDBOX_PERSONAL_PROFILE_ID =
            new ProfileId(System.getenv("TRANSFERWISE_SANDBOX_PERSONAL_ID"));
    protected static final ProfileId SANDBOX_BUSINESS_PROFILE_ID =
            new ProfileId(System.getenv("TRANSFERWISE_SANDBOX_BUSINESS_ID"));
    protected static final UserId SANDBOX_USER_ID = new UserId(System.getenv("TRANSFERWISE_SANDBOX_USER_ID"));

    @Test
    public void testSandboxTokenToLiveUrl() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new BaseApi(LIVE_BASEURL, SANDBOX_API_TOKEN));
        assertEquals("BaseUrl.stage [LIVE] doesn't match ApiToken.stage [SANDBOX]", e.getMessage());
    }

    @Test
    public void testLiveTokenToSandboxUrl() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new BaseApi(SANDBOX_BASEURL, LIVE_API_TOKEN));
        assertEquals("BaseUrl.stage [SANDBOX] doesn't match ApiToken.stage [LIVE]", e.getMessage());
    }

    @Test
    public void testLiveTokenToLiveUrl() {
        Exception e =
                assertThrows(UnsupportedOperationException.class, () -> new BaseApi(LIVE_BASEURL, LIVE_API_TOKEN));
        assertEquals("Not ready to hit https://api.transferwise.com", e.getMessage());
    }

}
