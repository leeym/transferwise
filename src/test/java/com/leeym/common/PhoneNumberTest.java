package com.leeym.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PhoneNumberTest {

    @Test
    public void testInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("12345"));
    }
}
