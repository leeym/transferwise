package com.leeym.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class AmountTest {

    @Test
    public void testUSD() {
        Amount amount = new Amount("USD", new BigDecimal("12345.67890"));
        Assertions.assertEquals("USD", amount.getCurrency());
        Assertions.assertEquals(12345.67890, amount.getValue().doubleValue());
        Assertions.assertEquals("Amount{currency='USD', value=12345.68}", amount.toString());
    }

    @Test
    public void testJEP() {
        Amount amount = new Amount("JEP", new BigDecimal("12345.67890"));
        Assertions.assertEquals("JEP", amount.getCurrency());
        Assertions.assertEquals(12345.67890, amount.getValue().doubleValue());
        Assertions.assertEquals("Amount{currency='JEP', value=12345.67890}", amount.toString());
    }
}