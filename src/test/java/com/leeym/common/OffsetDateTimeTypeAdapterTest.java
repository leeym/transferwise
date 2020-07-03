package com.leeym.common;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

class OffsetDateTimeTypeAdapterTest {

    private final OffsetDateTimeTypeAdapter adapter = new OffsetDateTimeTypeAdapter();
    private final OffsetDateTime localDateTime =
            OffsetDateTime.of(LocalDate.of(1977, 1, 12), LocalTime.of(12, 34, 56, 789000000), ZoneOffset.UTC);
    private final String value = "\"1977-01-12T12:34:56.789Z\"";

    @Test
    void write() throws IOException {
        StringWriter sw = new StringWriter();
        adapter.write(new JsonWriter(sw), localDateTime);
        Assertions.assertEquals(value, sw.toString());
    }

    @Test
    void read() throws IOException {
        Assertions.assertEquals(localDateTime, adapter.read(new JsonReader(new StringReader(value))));
    }
}
