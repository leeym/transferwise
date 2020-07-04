package com.leeym.common;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.leeym.api.Currencies.AUD;
import static com.leeym.api.Currencies.CAD;
import static com.leeym.api.Currencies.EUR;
import static com.leeym.api.Currencies.GBP;
import static com.leeym.api.Currencies.ILS;
import static com.leeym.api.Currencies.JPY;
import static com.leeym.api.Currencies.KRW;
import static com.leeym.api.Currencies.TWD;
import static com.leeym.api.Currencies.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AmountTest {

    private static final Amount ZERO_USD = new Amount(USD, BigDecimal.ZERO);
    private static final Amount ONE_USD = new Amount(USD, BigDecimal.ONE);
    private static final Amount ZERO_EUR = new Amount(EUR, BigDecimal.ZERO);
    private static final Amount ONE_EUR = new Amount(EUR, BigDecimal.ONE);
    private static final Amount NEGATIVE_ONE_EUR = new Amount(EUR, new BigDecimal("-1"));

    @Test
    public void testUSD() {
        Amount amount = new Amount(USD, new BigDecimal("12345.67890"));
        assertEquals(USD, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("US$12345.68", amount.toString());
    }

    @Test
    public void testJPY() {
        Amount amount = new Amount(JPY, new BigDecimal("12345.67890"));
        assertEquals(JPY, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("¥12346", amount.toString());
    }

    @Test
    public void testGBP() {
        Amount amount = new Amount(GBP, new BigDecimal("12345.67890"));
        assertEquals(GBP, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("£12345.68", amount.toString());
    }

    @Test
    public void testEUR() {
        Amount amount = new Amount(EUR, new BigDecimal("12345.67890"));
        assertEquals(EUR, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("€12345.68", amount.toString());
    }

    @Test
    public void testTWD() {
        Amount amount = new Amount(TWD, new BigDecimal("12345.67890"));
        assertEquals(TWD, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("$12345.68", amount.toString());
    }


    @Test
    public void testCAD() {
        Amount amount = new Amount(CAD, new BigDecimal("12345.67890"));
        assertEquals(CAD, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("CA$12345.68", amount.toString());
    }

    @Test
    public void testAUD() {
        Amount amount = new Amount(AUD, new BigDecimal("12345.67890"));
        assertEquals(AUD, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("AU$12345.68", amount.toString());
    }

    @Test
    public void testKRW() {
        Amount amount = new Amount(KRW, new BigDecimal("12345.67890"));
        assertEquals(KRW, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("￦12346", amount.toString());
    }

    @Test
    public void testILS() {
        Amount amount = new Amount(ILS, new BigDecimal("12345.67890"));
        assertEquals(ILS, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("₪12345.68", amount.toString());
    }

    @Test
    public void add() {
        assertEquals(ONE_USD, ONE_USD.add(ZERO_EUR));
        assertEquals(ONE_EUR, ZERO_USD.add(ONE_EUR));
        assertThrows(IllegalArgumentException.class, () -> ONE_USD.add(ONE_EUR));
        assertEquals(ZERO_USD, ZERO_USD.add(ZERO_EUR));
    }

    @Test
    public void subtract() {
        assertEquals(ONE_USD, ONE_USD.subtract(ZERO_EUR));
        assertEquals(NEGATIVE_ONE_EUR, ZERO_USD.subtract(ONE_EUR));
        assertThrows(IllegalArgumentException.class, () -> ONE_USD.subtract(ONE_EUR));
        assertEquals(ZERO_USD, ZERO_USD.subtract(ZERO_EUR));
    }
}
