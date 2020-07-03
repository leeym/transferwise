package com.leeym.api.quotes;

import com.leeym.api.BaseAPI;
import com.leeym.api.Stage;
import com.leeym.common.APIToken;

public class QuotesAPI extends BaseAPI {
    public QuotesAPI(Stage stage, APIToken token) {
        super(stage, token);
    }

    public QuoteResponse createQuote(QuoteRequest request) {
        String json = post("/v1/quotes", request);
        return gson.fromJson(json, QuoteResponse.class);
    }
}
