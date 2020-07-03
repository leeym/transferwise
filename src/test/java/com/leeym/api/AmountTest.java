package com.leeym.api;

import com.leeym.api.borderlessaccounts.Amount;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.leeym.api.Currencies.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AmountTest {

    @Test
    public void testUSD() {
        Amount amount = new Amount(USD, new BigDecimal("12345.67890"));
        assertEquals(USD, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("Amount{currency='USD', value=12345.68}", amount.toString());
    }
}
