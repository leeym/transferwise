package com.leeym.common;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalDateTypeAdapterTest {

    private final LocalDateTypeAdapter adapter = new LocalDateTypeAdapter();
    private final LocalDate localDate = LocalDate.of(1977, 1, 12);
    private final String value = "\"1977-01-12\"";

    @Test
    void write() throws IOException {
        StringWriter sw = new StringWriter();
        adapter.write(new JsonWriter(sw), localDate);
        assertEquals(value, sw.toString());
    }

    @Test
    void read() throws IOException {
        assertEquals(localDate, adapter.read(new JsonReader(new StringReader(value))));
    }
}
