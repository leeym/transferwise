package com.leeym.api.borderlessaccounts;

import com.leeym.api.ApiRequest;
import com.leeym.api.quotes.QuoteId;

public class ConversionRequest extends ApiRequest {
    QuoteId quoteId;

    public ConversionRequest(QuoteId quoteId) {
        this.quoteId = quoteId;
    }
}
