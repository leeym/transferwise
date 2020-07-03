package com.leeym.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PhoneNumberTest {

    @Test
    public void testInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("886227721165"));
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("16504636237"));
    }

    @Test
    public void testValidUS() {
        new PhoneNumber("+16504636237");
        new PhoneNumber("+1-6504636237");
        new PhoneNumber("+1(650)4636237");
        new PhoneNumber("+1(650)463-6237");
        new PhoneNumber("+1 (650) 463-6237");
        new PhoneNumber("+1-650-463-6237");
        new PhoneNumber("+1.650.463.6237");
    }

    @Test
    public void testValidTW() {
        new PhoneNumber("+886227721165");
        new PhoneNumber("+886-227721165");
        new PhoneNumber("+886(2)27721165");
        new PhoneNumber("+886 (2) 2772-1165");
        new PhoneNumber("+886-2-2772-1165");
        new PhoneNumber("+886.2.2772.1165");
    }
}
