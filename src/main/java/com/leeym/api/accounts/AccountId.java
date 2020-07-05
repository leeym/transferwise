package com.leeym.api.accounts;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.leeym.common.NumberId;

import java.io.IOException;

@JsonAdapter(AccountId.TypeAdapter.class)
public class AccountId extends NumberId {
    public AccountId(String value) {
        super(value);
    }

    static class TypeAdapter extends com.google.gson.TypeAdapter<AccountId> {
        @Override
        public void write(JsonWriter out, AccountId value) throws IOException {
            out.value(Long.parseLong(value.toString()));
        }

        @Override
        public AccountId read(JsonReader in) throws IOException {
            return new AccountId(in.nextString());
        }
    }
}
