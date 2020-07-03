package com.leeym.api.borderlessaccounts;

import com.leeym.api.BaseApi;
import com.leeym.api.Stage;
import com.leeym.common.ApiToken;
import com.leeym.common.ProfileId;

// https://api-docs.transferwise.com/#borderless-accounts
public class BorderlessAccountsApi extends BaseApi {
    public BorderlessAccountsApi(Stage stage, ApiToken token) {
        super(stage, token);
    }

    public BorderlessAccount getBorderlessAccount(ProfileId profileId) {
        String json = get("/v1/borderless-accounts?profileId=" + profileId);
        return gson.fromJson(json, BorderlessAccount[].class)[0];
    }

    public CurrencyPairs getCurrencyPairs() {
        String json = get("/v1/currency-pairs");
        return gson.fromJson(json, CurrencyPairs.class);
    }
}
