package com.leeym.common;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

@JsonAdapter(Swift.TypeAdapter.class)
public class Swift extends FormattedString {
    public Swift(String value) {
        super(value);
    }

    @Override
    String format() {
        return "^[A-Za-z]{4}[A-Za-z]{2}[A-Za-z0-9]{2}([A-Za-z0-9]{3})?$";
    }

    @Override
    String validate(String string) {
        return super.validate(string).toUpperCase();
    }

    static class TypeAdapter extends com.google.gson.TypeAdapter<Swift> {

        @Override
        public void write(JsonWriter out, Swift value) throws IOException {
            out.value(value.toString());
        }

        @Override
        public Swift read(JsonReader in) throws IOException {
            return new Swift(in.nextString());
        }
    }
}
