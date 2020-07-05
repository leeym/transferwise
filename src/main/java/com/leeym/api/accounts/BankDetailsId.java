package com.leeym.api.accounts;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.leeym.common.NumberId;

import java.io.IOException;

@JsonAdapter(BankDetailsId.TypeAdapter.class)
public class BankDetailsId extends NumberId {
    public BankDetailsId(String value) {
        super(value);
    }

    static class TypeAdapter extends com.google.gson.TypeAdapter<BankDetailsId> {

        @Override
        public void write(JsonWriter out, BankDetailsId value) throws IOException {
            out.value(Long.parseLong(value.toString()));
        }

        @Override
        public BankDetailsId read(JsonReader in) throws IOException {
            return new BankDetailsId(in.nextString());
        }
    }
}
