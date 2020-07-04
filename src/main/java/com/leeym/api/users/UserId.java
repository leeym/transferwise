package com.leeym.api.users;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.leeym.common.NumberId;

import java.io.IOException;

@JsonAdapter(UserId.TypeAdapter.class)
public class UserId extends NumberId {
    public UserId(String value) {
        super(value);
    }

    public class TypeAdapter extends com.google.gson.TypeAdapter<UserId> {
        @Override
        public void write(JsonWriter out, UserId value) throws IOException {
            out.value(Long.parseLong(value.toString()));
        }

        @Override
        public UserId read(JsonReader in) throws IOException {
            return new UserId(in.nextString());
        }
    }
}
