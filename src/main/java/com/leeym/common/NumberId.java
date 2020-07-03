package com.leeym.common;

public abstract class NumberId extends FormattedString {
    public NumberId(String value) {
        super(value);
    }

    @Override
    String format() {
        return "^\\d+$";
    }
}
