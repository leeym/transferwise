package com.leeym.api.quotes;

import com.leeym.api.BaseApi;
import com.leeym.common.ApiToken;
import com.leeym.common.BaseUrl;

// https://api-docs.transferwise.com/#quotes
public class QuotesApi extends BaseApi {
    public QuotesApi(BaseUrl baseUrl, ApiToken token) {
        super(baseUrl, token);
    }

    // https://api-docs.transferwise.com/#quotes-create
    public QuoteResponse createQuote(QuoteRequest request) {
        String json = post("/v1/quotes", request);
        return gson.fromJson(json, QuoteResponse.class);
    }
}
