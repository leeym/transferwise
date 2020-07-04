package com.leeym.common;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.io.IOException;

@JsonAdapter(PhoneNumber.TypeAdapter.class)
public class PhoneNumber extends FormattedString {

    private static final PhoneNumberUtil UTIL = PhoneNumberUtil.getInstance();

    public PhoneNumber(String value) {
        super(value);
    }

    @Override
    public String validate(String value) {
        final Phonenumber.PhoneNumber phoneNumber;
        try {
            phoneNumber = UTIL.parse(value, "ZZ");
        } catch (NumberParseException e) {
            throw new IllegalArgumentException(e);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("+").append(phoneNumber.getCountryCode()).append(phoneNumber.getNationalNumber());
        return sb.toString();
    }

    static class TypeAdapter extends com.google.gson.TypeAdapter<PhoneNumber> {
        @Override
        public void write(JsonWriter out, PhoneNumber value) throws IOException {
            out.value(value.toString());
        }

        @Override
        public PhoneNumber read(JsonReader in) throws IOException {
            return new PhoneNumber(in.nextString());
        }
    }
}
