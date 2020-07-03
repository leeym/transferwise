package com.leeym.common;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

@JsonAdapter(Email.TypeAdapter.class)
public class Email extends FormattedString {
    public Email(String value) {
        super(value);
    }

    @Override
    String format() {
        return "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    }

    static class TypeAdapter extends com.google.gson.TypeAdapter<Email> {

        @Override
        public void write(JsonWriter out, Email value) throws IOException {
            out.value(value.toString());
        }

        @Override
        public Email read(JsonReader in) throws IOException {
            return new Email(in.nextString());
        }
    }

}
