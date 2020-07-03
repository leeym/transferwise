package com.leeym.api.users;

import com.leeym.api.BaseApi;
import com.leeym.api.Stage;
import com.leeym.common.ApiToken;

// https://api-docs.transferwise.com/#users
public class UsersApi extends BaseApi {

    public UsersApi(Stage stage, ApiToken token) {
        super(stage, token);
    }

    // https://api-docs.transferwise.com/#users-get-the-currently-logged-in-user
    public User getMe() {
        String json = get("/v1/me");
        return gson.fromJson(json, User.class);
    }

    // https://api-docs.transferwise.com/#users-get-by-id
    public User getUser(UserId userId) {
        String json = get("/v1/users/" + userId);
        return gson.fromJson(json, User.class);
    }
}
