package com.leeym.api.accounts;

import com.leeym.api.BaseApi;
import com.leeym.api.profiles.ProfileId;
import com.leeym.api.quotes.QuoteId;
import com.leeym.common.ApiToken;
import com.leeym.common.BaseUrl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// https://api-docs.transferwise.com/#borderless-accounts
public class AccountsApi extends BaseApi {
    public AccountsApi(BaseUrl baseUrl, ApiToken token) {
        super(baseUrl, token);
    }

    // https://api-docs.transferwise.com/#borderless-accounts-get-account-balance
    public Account getAccount(ProfileId profileId) {
        String json = get("/v1/borderless-accounts?profileId=" + profileId);
        return gson.fromJson(json, Account[].class)[0];
    }

    // https://api-docs.transferwise.com/#borderless-accounts-convert-currencies
    public Conversion executeQuoteAndConvert(AccountId accountId, QuoteId quoteId) {
        ConversionRequest request = new ConversionRequest(quoteId);
        String json = post("/v1/borderless-accounts/" + accountId + "/conversions", request);
        return gson.fromJson(json, Conversion.class);
    }

    // https://api-docs.transferwise.com/#borderless-accounts-get-currency-pairs
    public CurrencyPairs getCurrencyPairs() {
        String json = get("/v1/currency-pairs");
        return gson.fromJson(json, CurrencyPairs.class);
    }

    // https://api-docs.transferwise.com/#borderless-accounts-get-available-currencies
    public List<BalanceCurrency> getBalanceCurrencies() {
        String json = get("/v1/borderless-accounts/balance-currencies");
        return Arrays.stream(gson.fromJson(json, BalanceCurrency[].class)).collect(Collectors.toList());
    }
}
