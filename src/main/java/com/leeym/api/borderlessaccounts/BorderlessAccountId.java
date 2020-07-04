package com.leeym.api.borderlessaccounts;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.leeym.common.NumberId;

import java.io.IOException;

@JsonAdapter(BorderlessAccountId.TypeAdapter.class)
public class BorderlessAccountId extends NumberId {
    public BorderlessAccountId(String value) {
        super(value);
    }

    static class TypeAdapter extends com.google.gson.TypeAdapter<BorderlessAccountId> {
        @Override
        public void write(JsonWriter out, BorderlessAccountId value) throws IOException {
            out.value(Long.parseLong(value.toString()));
        }

        @Override
        public BorderlessAccountId read(JsonReader in) throws IOException {
            return new BorderlessAccountId(in.nextString());
        }
    }
}
