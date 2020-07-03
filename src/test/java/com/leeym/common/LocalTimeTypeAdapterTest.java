package com.leeym.common;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalTime;

class LocalTimeTypeAdapterTest {

    private final LocalTimeTypeAdapter adapter = new LocalTimeTypeAdapter();
    private final LocalTime localTime = LocalTime.of(12, 34, 56);
    private final String value = "\"12:34:56\"";

    @Test
    void write() throws IOException {
        StringWriter sw = new StringWriter();
        adapter.write(new JsonWriter(sw), localTime);
        Assertions.assertEquals(value, sw.toString());
    }

    @Test
    void read() throws IOException {
        Assertions.assertEquals(localTime, adapter.read(new JsonReader(new StringReader(value))));
    }
}
