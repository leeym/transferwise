package com.leeym.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IBANTest {

    @Test
    public void valid() {
        new IBAN("DE89370400440532013000");
        new IBAN("DE89 3704 0044 0532 0130 00");
        new IBAN("GB33BUKB20201555555555");
        new IBAN("DE75512108001245126199");
        new IBAN("FR7630006000011234567890189");
    }

    @Test
    public void invalid() {
        assertThrows(IllegalArgumentException.class, () -> new IBAN("GB33BUKB20201555987654"));
        assertThrows(IllegalArgumentException.class, () -> new IBAN("GB33B UKB20 20155 55555 55"));
    }
}