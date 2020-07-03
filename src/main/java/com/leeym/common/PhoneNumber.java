package com.leeym.common;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(PhoneNumberTypeAdapter.class)
public class PhoneNumber extends FormattedString {
    public PhoneNumber(String value) {
        super(value);
    }

    @Override
    String format() {
        return "^(\\+\\d{1,3}\\D?)(\\d{1,4}|\\(\\d{1,4}\\))(\\D?\\d{2,4}){2,4}?";
    }
}
