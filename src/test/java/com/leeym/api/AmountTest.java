package com.leeym.api;

import com.leeym.api.borderlessaccounts.Amount;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AmountTest {

    @Test
    public void testUSD() {
        Amount amount = new Amount(Currency.getInstance("USD"), new BigDecimal("12345.67890"));
        assertEquals(Currency.getInstance("USD"), amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("Amount{currency='USD', value=12345.68}", amount.toString());
    }
}
