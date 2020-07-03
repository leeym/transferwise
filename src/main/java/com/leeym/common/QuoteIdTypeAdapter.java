package com.leeym.common;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class QuoteIdTypeAdapter extends TypeAdapter<QuoteId> {
    @Override
    public void write(JsonWriter out, QuoteId value) throws IOException {
        out.value(value.toString());
    }

    @Override
    public QuoteId read(JsonReader in) throws IOException {
        return new QuoteId(in.nextString());
    }
}
