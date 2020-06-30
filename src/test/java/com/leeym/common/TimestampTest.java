package com.leeym.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.*;

class TimestampTest {

    @Test
    public void testNull() {
        Exception e = Assertions.assertThrows(NullPointerException.class, () -> new Timestamp(null));
        Assertions.assertEquals("Timestamp can't be null", e.getMessage());
    }

    @Test
    public void testEmpty() {
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new Timestamp(""));
        Assertions.assertEquals("Timestamp can't be empty", e.getMessage());
    }

    @Test
    public void testInvalid() {
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new Timestamp("foobar"));
        Assertions.assertEquals("Timestamp [foobar] doesn't match [^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$]", e.getMessage());
    }

    @Test
    public void testValid() {
        Assertions.assertDoesNotThrow(() -> new Timestamp("2020-06-27T07:55:17.367Z"));
    }

    @Test
    public void testOffsetDateTime() {
        Timestamp t = new Timestamp("2020-06-27T07:55:17.367Z");
        OffsetDateTime odt = t.getOffsetDateTime();
        Assertions.assertEquals(2020, odt.getYear());
        Assertions.assertEquals(Month.JUNE, odt.getMonth());
        Assertions.assertEquals(27, odt.getDayOfMonth());
        Assertions.assertEquals(7, odt.getHour());
        Assertions.assertEquals(55, odt.getMinute());
        Assertions.assertEquals(17, odt.getSecond());
        Assertions.assertEquals(367000000, odt.getNano());
        Assertions.assertEquals(ZoneOffset.UTC, odt.getOffset());
    }
}