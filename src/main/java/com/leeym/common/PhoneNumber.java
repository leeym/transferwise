package com.leeym.common;

public class PhoneNumber extends FormattedString {
    public PhoneNumber(String value) {
        super(value);
    }

    @Override
    String format() {
        return "^(\\+\\d{1,3})(\\d{2,4}|\\(\\d{2,4}\\))(\\D?\\d{2,4}){2,4}?";
    }
}
