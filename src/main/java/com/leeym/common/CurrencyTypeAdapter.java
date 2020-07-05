package com.leeym.common;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Currency;

public class CurrencyTypeAdapter extends TypeAdapter<Currency> {
    @Override
    public void write(JsonWriter out, Currency value) throws IOException {
        out.value(value.getCurrencyCode());
    }

    @Override
    public Currency read(JsonReader in) throws IOException {
        try {
            return Currency.getInstance(in.nextString());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
