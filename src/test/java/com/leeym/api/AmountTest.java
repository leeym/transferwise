package com.leeym.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

class AmountTest {

    @Test
    public void testUSD() {
        Amount amount = new Amount(Currency.getInstance("USD"), new BigDecimal("12345.67890"));
        Assertions.assertEquals(Currency.getInstance("USD"), amount.getCurrency());
        Assertions.assertEquals(12345.67890, amount.getValue().doubleValue());
        Assertions.assertEquals("Amount{currency='USD', value=12345.68}", amount.toString());
    }
}