package com.leeym.api.borderlessaccounts;

import com.leeym.api.BaseApi;
import com.leeym.api.quotes.QuoteId;
import com.leeym.api.userprofiles.ProfileId;
import com.leeym.common.ApiToken;
import com.leeym.common.BaseUrl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// https://api-docs.transferwise.com/#borderless-accounts
public class BorderlessAccountsApi extends BaseApi {
    public BorderlessAccountsApi(BaseUrl baseUrl, ApiToken token) {
        super(baseUrl, token);
    }

    public BorderlessAccount getBorderlessAccount(ProfileId profileId) {
        String json = get("/v1/borderless-accounts?profileId=" + profileId);
        return gson.fromJson(json, BorderlessAccount[].class)[0];
    }

    public ConversionResponse executeQuoteAndConvert(BorderlessAccountId borderlessAccountId, QuoteId quoteId) {
        ConversionRequest request = new ConversionRequest(quoteId);
        String json = post("/v1/borderless-accounts/" + borderlessAccountId + "/conversions", request);
        return gson.fromJson(json, ConversionResponse.class);
    }

    public CurrencyPairs getCurrencyPairs() {
        String json = get("/v1/currency-pairs");
        return gson.fromJson(json, CurrencyPairs.class);
    }

    public List<BalanceCurrency> getBalanceCurrencies() {
        String json = get("/v1/borderless-accounts/balance-currencies");
        return Arrays.stream(gson.fromJson(json, BalanceCurrency[].class)).collect(Collectors.toList());
    }
}
