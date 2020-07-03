package com.leeym.common;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OffsetDateTimeTypeAdapterTest {

    private final OffsetDateTimeTypeAdapter adapter = new OffsetDateTimeTypeAdapter();
    private final OffsetDateTime localDateTime = OffsetDateTime.of(
            LocalDate.of(2020, 7, 3),
            LocalTime.of(13, 27, 58, 0),
            ZoneOffset.UTC);
    private final String value = "\"2020-07-03T13:27:58+0000\"";

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
