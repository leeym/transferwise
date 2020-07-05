package com.leeym.api.quotes;

import com.leeym.api.BaseApi;
import com.leeym.api.userprofiles.ProfileId;
import com.leeym.common.ApiToken;
import com.leeym.common.BaseUrl;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.stream.Collectors;

import static com.leeym.api.quotes.Type.BALANCE_CONVERSION;

// https://api-docs.transferwise.com/#quotes
public class QuotesApi extends BaseApi {
    public QuotesApi(BaseUrl baseUrl, ApiToken token) {
        super(baseUrl, token);
    }

    // https://api-docs.transferwise.com/#quotes-create
    private QuoteResponse createQuote(QuoteRequest request) {
        String json = post("/v1/quotes", request);
        return gson.fromJson(json, QuoteResponse.class);
    }

    public QuoteResponse buyTargetFromSource(ProfileId profileId, Currency source, Currency target, BigDecimal value) {
        QuoteResponse response = createQuote(new QuoteRequest(profileId, source, target, value, BALANCE_CONVERSION));
        if (response.hasErrors()) {
            throw new RuntimeException(response.getErrors().stream()
                    .map(QuoteResponse.Error::getMessage)
                    .collect(Collectors.joining(" ")));
        }
        return response;
    }

    public QuoteResponse sellSourceToTarget(ProfileId profileId, Currency source, BigDecimal value, Currency target) {
        QuoteResponse response = createQuote(new QuoteRequest(profileId, source, value, target, BALANCE_CONVERSION));
        if (response.hasErrors()) {
            throw new RuntimeException(response.getErrors().stream()
                    .map(QuoteResponse.Error::getMessage)
                    .collect(Collectors.joining(" ")));
        }
        return response;
    }
}
