package com.leeym.api.users;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class UserIdTypeAdapter extends TypeAdapter<UserId> {
    @Override
    public void write(JsonWriter out, UserId value) throws IOException {
        out.value(value.toString());
    }

    @Override
    public UserId read(JsonReader in) throws IOException {
        return new UserId(in.nextString());
    }
}
