package com.leeym.api.borderlessaccounts;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.leeym.common.NumberId;

import java.io.IOException;

@JsonAdapter(BorderlessAcountId.TypeAdapter.class)
public class BorderlessAcountId extends NumberId {
    public BorderlessAcountId(String value) {
        super(value);
    }

    static class TypeAdapter extends com.google.gson.TypeAdapter<BorderlessAcountId> {
        @Override
        public void write(JsonWriter out, BorderlessAcountId value) throws IOException {
            out.value(value.toString());
        }

        @Override
        public BorderlessAcountId read(JsonReader in) throws IOException {
            return new BorderlessAcountId(in.nextString());
        }
    }
}
