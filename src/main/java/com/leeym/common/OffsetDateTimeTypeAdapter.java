package com.leeym.common;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.OffsetDateTime;

public class OffsetDateTimeTypeAdapter extends TypeAdapter<OffsetDateTime> {
    @Override
    public void write(JsonWriter out, OffsetDateTime value) throws IOException {
        out.value(value.toString().replaceAll("Z$", "+0000"));
    }

    @Override
    public OffsetDateTime read(JsonReader in) throws IOException {
        return OffsetDateTime.parse(in.nextString().replaceAll("0000$", "00:00"));
    }
}
