package com.leeym.common;

public class QuoteId extends FormattedString {
    public QuoteId(String value) {
        super(value);
    }

    @Override
    String format() {
        return "^\\d+$";
    }
}
