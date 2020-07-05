package com.leeym.api.accounts;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.leeym.common.NumberId;

import java.io.IOException;

@JsonAdapter(ConversionId.TypeAdapter.class)
public class ConversionId extends NumberId {
    public ConversionId(String value) {
        super(value);
    }

    static class TypeAdapter extends com.google.gson.TypeAdapter<ConversionId> {
        @Override
        public void write(JsonWriter out, ConversionId value) throws IOException {
            out.value(Long.parseLong(value.toString()));
        }

        @Override
        public ConversionId read(JsonReader in) throws IOException {
            return new ConversionId(in.nextString());
        }
    }
}
