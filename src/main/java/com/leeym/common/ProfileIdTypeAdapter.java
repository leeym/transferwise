package com.leeym.common;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ProfileIdTypeAdapter extends TypeAdapter<ProfileId> {
    @Override
    public void write(JsonWriter out, ProfileId value) throws IOException {
        out.value(value.toString());
    }

    @Override
    public ProfileId read(JsonReader in) throws IOException {
        return new ProfileId(in.nextString());
    }
}
