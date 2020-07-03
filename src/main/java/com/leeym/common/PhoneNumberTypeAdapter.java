package com.leeym.common;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class PhoneNumberTypeAdapter extends TypeAdapter<PhoneNumber> {
    @Override
    public void write(JsonWriter out, PhoneNumber value) throws IOException {
        out.value(value.toString());
    }

    @Override
    public PhoneNumber read(JsonReader in) throws IOException {
        return new PhoneNumber(in.nextString());
    }
}
