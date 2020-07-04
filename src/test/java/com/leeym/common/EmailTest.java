package com.leeym.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @Test
    public void testValid() {
        new Email("leeym@leeym.com");
        new Email("leeym.tw@leeym.com");
        new Email("leeym@yahoo.com.tw");
    }

    @Test
    public void testInvalid() {
        assertThrows(NullPointerException.class, () -> new Email(null));
        assertThrows(IllegalArgumentException.class, () -> new Email(""));
        assertThrows(IllegalArgumentException.class, () -> new Email("leeym"));
        assertThrows(IllegalArgumentException.class, () -> new Email("(leeym)@leeym.com"));
        assertThrows(IllegalArgumentException.class, () -> new Email("leeym@leeym@leeym.com"));
    }
}
