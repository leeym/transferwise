package com.leeym.api.accounts;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.leeym.common.NumberId;

import java.io.IOException;

@JsonAdapter(RecipientId.TypeAdapter.class)
public class RecipientId extends NumberId {
    public RecipientId(String value) {
        super(value);
    }

    static class TypeAdapter extends com.google.gson.TypeAdapter<RecipientId> {
        @Override
        public void write(JsonWriter out, RecipientId value) throws IOException {
            out.value(Long.parseLong(value.toString()));
        }

        @Override
        public RecipientId read(JsonReader in) throws IOException {
            return new RecipientId(in.nextString());
        }
    }
}
