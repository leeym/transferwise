package com.leeym.api.borderlessaccounts;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.leeym.common.NumberId;

import java.io.IOException;

@JsonAdapter(StepId.TypeAdapter.class)
public class StepId extends NumberId {
    public StepId(String value) {
        super(value);
    }

    static class TypeAdapter extends com.google.gson.TypeAdapter<StepId> {

        @Override
        public void write(JsonWriter out, StepId value) throws IOException {
            out.value(Long.parseLong(value.toString()));
        }

        @Override
        public StepId read(JsonReader in) throws IOException {
            return new StepId(in.nextString());
        }
    }
}
