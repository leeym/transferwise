package com.leeym.api.quotes;

import com.leeym.api.BaseApi;
import com.leeym.api.profiles.ProfileId;
import com.leeym.common.ApiToken;
import com.leeym.common.BaseUrl;

import java.math.BigDecimal;
import java.util.Currency;

import static com.leeym.api.quotes.Type.BALANCE_CONVERSION;

// https://api-docs.transferwise.com/#quotes
public class QuotesApi extends BaseApi {
    public QuotesApi(BaseUrl baseUrl, ApiToken token) {
        super(baseUrl, token);
    }

    // https://api-docs.transferwise.com/#quotes-create
    private Quote createQuote(QuoteRequest request) {
        String json = post("/v1/quotes", request);
        return gson.fromJson(json, Quote.class);
    }

    public Quote buyTargetFromSource(ProfileId profileId, Currency source, Currency target, BigDecimal value) {
        return createQuote(new QuoteRequest(profileId, source, target, value, BALANCE_CONVERSION));
    }

    public Quote sellSourceToTarget(ProfileId profileId, Currency source, BigDecimal value, Currency target) {
        return createQuote(new QuoteRequest(profileId, source, value, target, BALANCE_CONVERSION));
    }
}
