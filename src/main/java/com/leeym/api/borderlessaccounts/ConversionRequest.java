package com.leeym.api.borderlessaccounts;

import com.leeym.api.ApiRequest;
import com.leeym.api.quotes.QuoteId;

public class ConversionRequest extends ApiRequest {
    QuoteId quoteid;

    public ConversionRequest(QuoteId quoteId) {
        this.quoteid = quoteId;
    }
}
