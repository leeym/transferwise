package com.leeym.api.borderlessaccounts;

import com.leeym.api.BaseAPI;
import com.leeym.api.Stage;
import com.leeym.common.APIToken;
import com.leeym.common.ProfileId;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// https://api-docs.transferwise.com/#borderless-accounts
public class BorderlessAccountsAPI extends BaseAPI {
    public BorderlessAccountsAPI(Stage stage, APIToken token) {
        super(stage, token);
    }

    public List<BorderlessAccount> getBorderlessAccounts(ProfileId profileId) {
        String json = get("/v1/borderless-accounts?profileId=" + profileId);
        BorderlessAccount[] borderlessAccounts = gson.fromJson(json, BorderlessAccount[].class);
        return Arrays.stream(borderlessAccounts).collect(Collectors.toList());
    }

    public CurrencyPairs getCurrencyPairs() {
        String json = get("/v1/currency-pairs");
        return gson.fromJson(json, CurrencyPairs.class);
    }
}
