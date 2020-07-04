package com.leeym.api.borderlessaccounts;

import com.leeym.api.BaseApi;
import com.leeym.common.ApiToken;
import com.leeym.common.BaseUrl;
import com.leeym.api.userprofiles.ProfileId;

// https://api-docs.transferwise.com/#borderless-accounts
public class BorderlessAccountsApi extends BaseApi {
    public BorderlessAccountsApi(BaseUrl baseUrl, ApiToken token) {
        super(baseUrl, token);
    }

    public BorderlessAccount getBorderlessAccount(ProfileId profileId) {
        String json = get("/v1/borderless-accounts?profileId=" + profileId);
        return gson.fromJson(json, BorderlessAccount[].class)[0];
    }
}
