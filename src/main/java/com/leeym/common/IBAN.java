package com.leeym.common;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.iban4j.IbanFormat;
import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;
import org.iban4j.InvalidCheckDigitException;
import org.iban4j.UnsupportedCountryException;

import java.io.IOException;

@JsonAdapter(IBAN.TypeAdapter.class)
public class IBAN extends FormattedString {
    public IBAN(String value) {
        super(value);
    }

    @Override
    public String validate(String string) {
        try {
            if (string.contains(" ")) {
                IbanUtil.validate(string, IbanFormat.Default);
            } else {
                IbanUtil.validate(string);
            }
            return string;
        } catch (IbanFormatException | InvalidCheckDigitException | UnsupportedCountryException e) {
            throw new IllegalArgumentException(e);
        }
    }

    static class TypeAdapter extends com.google.gson.TypeAdapter<IBAN> {

        @Override
        public void write(JsonWriter out, IBAN value) throws IOException {
            out.value(value.toString());
        }

        @Override
        public IBAN read(JsonReader in) throws IOException {
            return new IBAN(in.nextString());
        }
    }
}
