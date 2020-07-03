package com.leeym.api.quotes;

import com.leeym.api.BaseApi;
import com.leeym.api.Stage;
import com.leeym.common.ApiToken;

public class QuotesApi extends BaseApi {
    public QuotesApi(Stage stage, ApiToken token) {
        super(stage, token);
    }

    public QuoteResponse createQuote(QuoteRequest request) {
        String json = post("/v1/quotes", request);
        return gson.fromJson(json, QuoteResponse.class);
    }
}
