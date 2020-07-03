package com.leeym.api.users;

import com.leeym.api.BaseApiITCase;
import com.leeym.api.Stage;
import org.junit.jupiter.api.Test;

class UsersApiITCase extends BaseApiITCase {

    private final UsersApi api = new UsersApi(Stage.SANDBOX, REAL_SANDBOX_API_TOKEN);

    @Test
    void getMe() {
        System.err.println(api.getMe());
    }

    @Test
    void getUser() {
        System.err.println(api.getUser(REAL_SANDBOX_USER_ID));
    }
}
