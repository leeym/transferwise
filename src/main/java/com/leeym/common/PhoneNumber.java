package com.leeym.common;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

@JsonAdapter(PhoneNumber.TypeAdapter.class)
public class PhoneNumber extends FormattedString {
    public PhoneNumber(String value) {
        super(value);
    }

    @Override
    String format() {
        return "^(\\+\\d{1,3}\\D?)(\\d{1,4}|\\(\\d{1,4}\\))(\\D?\\d{2,4}){2,4}?";
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
