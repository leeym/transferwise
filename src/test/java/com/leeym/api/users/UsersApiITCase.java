package com.leeym.api.users;

import com.leeym.api.BaseApiITCase;
import org.junit.jupiter.api.Test;

import static com.leeym.common.BaseUrl.SANDBOX_BASEURL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsersApiITCase extends BaseApiITCase {

    private final UsersApi api = new UsersApi(SANDBOX_BASEURL, SANDBOX_API_TOKEN);

    @Test
    void getMe() {
        User user = api.getMe();
        assertEquals(SANDBOX_USER_ID, user.id);
        assertTrue(user.active);
    }

    @Test
    void getUser() {
        User user = api.getUser(SANDBOX_USER_ID);
        assertEquals(SANDBOX_USER_ID, user.id);
        assertTrue(user.active);
    }
}
