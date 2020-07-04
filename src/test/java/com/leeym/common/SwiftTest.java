package com.leeym.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SwiftTest {

    @Test
    public void valid() {
        assertEquals("CITITWTX", new Swift("CITITWTX").toString());
        assertEquals("CITITWTX", new Swift("cititwtx").toString());
    }

    @Test
    public void invalid() {
        assertThrows(IllegalArgumentException.class, () -> new Swift("12345678"));
    }
}