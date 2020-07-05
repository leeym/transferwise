package com.leeym.common;

import com.leeym.api.rates.Rate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static com.leeym.api.Currencies.AUD;
import static com.leeym.api.Currencies.CAD;
import static com.leeym.api.Currencies.EUR;
import static com.leeym.api.Currencies.GBP;
import static com.leeym.api.Currencies.ILS;
import static com.leeym.api.Currencies.JPY;
import static com.leeym.api.Currencies.KRW;
import static com.leeym.api.Currencies.SGD;
import static com.leeym.api.Currencies.TWD;
import static com.leeym.api.Currencies.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AmountTest {

    private static final BigDecimal VALUE = new BigDecimal("1234.567890");
    private static final Amount NEGATIVE_ONE_USD = new Amount(USD, BigDecimal.ONE.negate());
    private static final Amount ZERO_USD = new Amount(USD, BigDecimal.ZERO);
    private static final Amount ONE_USD = new Amount(USD, BigDecimal.ONE);
    private static final Amount TWO_USD = new Amount(USD, new BigDecimal("2"));
    private static final Amount ONE_EUR = new Amount(EUR, BigDecimal.ONE);
    private final Logger logger = Logger.getAnonymousLogger();

    @Test
    public void isPositive() {
        assertFalse(NEGATIVE_ONE_USD.isPositive());
        assertFalse(ZERO_USD.isPositive());
        assertTrue(ONE_USD.isPositive());
    }

    @Test
    public void isNegative() {
        assertTrue(NEGATIVE_ONE_USD.isNegative());
        assertFalse(ZERO_USD.isPositive());
        assertFalse(ONE_USD.isNegative());
    }

    @Test
    public void isZero() {
        assertFalse(NEGATIVE_ONE_USD.isZero());
        assertTrue(ZERO_USD.isZero());
        assertFalse(ONE_USD.isZero());
    }

    @Test
    public void isGreaterThan() {
        assertTrue(ZERO_USD.isGreaterThan(NEGATIVE_ONE_USD));
        assertFalse(ZERO_USD.isGreaterThan(ZERO_USD));
        assertFalse(ZERO_USD.isGreaterThan(ONE_USD));
    }

    @Test
    public void isLessThan() {
        assertFalse(ZERO_USD.isLessThan(NEGATIVE_ONE_USD));
        assertFalse(ZERO_USD.isLessThan(ZERO_USD));
        assertTrue(ZERO_USD.isLessThan(ONE_USD));
    }

    @Test
    public void equals() {
        assertNotEquals(ZERO_USD, NEGATIVE_ONE_USD);
        assertEquals(ZERO_USD, ZERO_USD);
        assertNotEquals(ZERO_USD, ONE_USD);
    }

    @Test
    public void testUSD() {
        Amount amount = new Amount(USD, VALUE);
        assertEquals(USD, amount.getCurrency());
        assertEquals(VALUE, amount.getValue());
        assertEquals("US$ 1234.57", amount.toString());
    }

    @Test
    public void testJPY() {
        Amount amount = new Amount(JPY, VALUE);
        assertEquals(JPY, amount.getCurrency());
        assertEquals(VALUE, amount.getValue());
        assertEquals("¥ 1235", amount.toString());
    }

    @Test
    public void testGBP() {
        Amount amount = new Amount(GBP, VALUE);
        assertEquals(GBP, amount.getCurrency());
        assertEquals(VALUE, amount.getValue());
        assertEquals("£ 1234.57", amount.toString());
    }

    @Test
    public void testEUR() {
        Amount amount = new Amount(EUR, VALUE);
        assertEquals(EUR, amount.getCurrency());
        assertEquals(VALUE, amount.getValue());
        assertEquals("€ 1234.57", amount.toString());
    }

    @Test
    public void testSGD() {
        Amount amount = new Amount(SGD, VALUE);
        assertEquals(SGD, amount.getCurrency());
        assertEquals(VALUE, amount.getValue());
        assertEquals("SGD 1234.57", amount.toString());
    }

    @Test
    public void testTWD() {
        Amount amount = new Amount(TWD, VALUE);
        assertEquals(TWD, amount.getCurrency());
        assertEquals(VALUE, amount.getValue());
        assertEquals("$ 1234.57", amount.toString());
    }


    @Test
    public void testCAD() {
        Amount amount = new Amount(CAD, VALUE);
        assertEquals(CAD, amount.getCurrency());
        assertEquals(VALUE, amount.getValue());
        assertEquals("CA$ 1234.57", amount.toString());
    }

    @Test
    public void testAUD() {
        Amount amount = new Amount(AUD, VALUE);
        assertEquals(AUD, amount.getCurrency());
        assertEquals(VALUE, amount.getValue());
        assertEquals("AU$ 1234.57", amount.toString());
    }

    @Test
    public void testKRW() {
        Amount amount = new Amount(KRW, VALUE);
        assertEquals(KRW, amount.getCurrency());
        assertEquals(VALUE, amount.getValue());
        assertEquals("￦ 1235", amount.toString());
    }

    @Test
    public void testILS() {
        Amount amount = new Amount(ILS, VALUE);
        assertEquals(ILS, amount.getCurrency());
        assertEquals(VALUE, amount.getValue());
        assertEquals("₪ 1234.57", amount.toString());
    }

    @Test
    public void validAdd() {
        assertEquals(TWO_USD, ONE_USD.add(ONE_USD));
    }

    @Test
    public void invalidAdd() {
        assertThrows(IllegalArgumentException.class, () -> ONE_USD.add(ONE_EUR));
    }

    @Test
    public void validSubtract() {
        assertEquals(ONE_USD, TWO_USD.subtract(ONE_USD));
    }

    @Test
    public void invalidSubtract() {
        assertThrows(IllegalArgumentException.class, () -> TWO_USD.subtract(ONE_EUR));
    }

    @Test
    public void validMultiplyRate() {
        Rate rate = new Rate(USD, EUR, 0.889284917);
        Amount source = new Amount(USD, new BigDecimal("1000"));
        Amount target = source.multiply(rate);
        assertEquals(EUR, target.getCurrency());
        assertEquals("€ 889.28", target.toString());
    }

    @Test
    public void invalidMultiplyRate() {
        Rate rate = new Rate(USD, EUR, 0.889284917);
        Amount source = new Amount(EUR, new BigDecimal("1000"));
        assertThrows(IllegalArgumentException.class, () -> source.multiply(rate));
    }

    @Test
    public void validDivideRate() {
        Rate rate = new Rate(USD, EUR, 0.889284917);
        Amount source = new Amount(EUR, new BigDecimal("1000"));
        Amount target = source.divide(rate);
        assertEquals(USD, target.getCurrency());
        assertEquals("US$ 1124.50", target.toString());
    }

    @Test
    public void invalidDivideRate() {
        Rate rate = new Rate(USD, EUR, 0.889284917);
        Amount source = new Amount(USD, new BigDecimal("1000"));
        assertThrows(IllegalArgumentException.class, () -> source.divide(rate));
    }

    @Test
    public void divideDouble() {
        Amount dividend = new Amount(USD, BigDecimal.TEN);
        Amount divisor = new Amount(USD, BigDecimal.ONE);
        assertEquals(10, dividend.divide(divisor));
    }
}
