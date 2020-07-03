package com.leeym.common;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(QuoteIdTypeAdapter.class)
public class QuoteId extends FormattedString {
    public QuoteId(String value) {
        super(value);
    }

    @Override
    String format() {
        return "^\\d+$";
    }
}
