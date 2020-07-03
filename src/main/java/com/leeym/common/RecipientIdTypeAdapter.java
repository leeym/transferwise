package com.leeym.common;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class RecipientIdTypeAdapter extends TypeAdapter<RecipientId> {
    @Override
    public void write(JsonWriter out, RecipientId value) throws IOException {
        out.value(value.toString());
    }

    @Override
    public RecipientId read(JsonReader in) throws IOException {
        return new RecipientId(in.nextString());
    }
}
