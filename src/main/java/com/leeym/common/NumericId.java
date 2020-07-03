package com.leeym.common;

public abstract class NumericId extends FormattedString {
    public NumericId(String value) {
        super(value);
    }

    @Override
    String format() {
        return "^\\d+$";
    }
}
