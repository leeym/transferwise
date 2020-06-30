package com.leeym.common;

import java.util.Currency;

public class CurrencyCode extends FormattedString {

    public CurrencyCode(String value) {
        super(value);
    }

    @Override
    String format() {
        return "^[A-Z]{3}$";
    }

    public Currency toCurrency() {
        return Currency.getInstance(value);
    }
}
