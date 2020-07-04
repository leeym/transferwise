package com.leeym.api.quotes;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.leeym.common.NumberId;

import java.io.IOException;

@JsonAdapter(QuoteId.TypeAdapter.class)
public class QuoteId extends NumberId {
    public QuoteId(String value) {
        super(value);
    }

    static class TypeAdapter extends com.google.gson.TypeAdapter<QuoteId> {
        @Override
        public void write(JsonWriter out, QuoteId value) throws IOException {
            out.value(value.toString());
        }

        @Override
        public QuoteId read(JsonReader in) throws IOException {
            return new QuoteId(in.nextString());
        }
    }
}
