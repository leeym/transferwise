package com.leeym.common;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalDateTimeTypeAdapterTest {

    private final LocalDateTimeTypeAdapter adapter = new LocalDateTimeTypeAdapter();
    private final LocalDateTime localDateTime = LocalDateTime.of(1977, 1, 12, 12, 34, 56);
    private final String value = "\"1977-01-12T12:34:56\"";

    @Test
    void write() throws IOException {
        StringWriter sw = new StringWriter();
        adapter.write(new JsonWriter(sw), localDateTime);
        assertEquals(value, sw.toString());
    }

    @Test
    void read() throws IOException {
        assertEquals(localDateTime, adapter.read(new JsonReader(new StringReader(value))));
    }
}
