package com.leeym.common;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
import static org.junit.jupiter.api.Assertions.assertThrows;

class AmountTest {

    private static final BigDecimal VALUE = new BigDecimal("12345.67890");
    private static final Amount ONE_USD = new Amount(USD, BigDecimal.ONE);
    private static final Amount TWO_USD = new Amount(USD, new BigDecimal("2"));
    private static final Amount ONE_EUR = new Amount(EUR, BigDecimal.ONE);
    private final Logger logger = Logger.getAnonymousLogger();

    @Test
    public void testUSD() {
        Amount amount = new Amount(USD, VALUE);
        assertEquals(USD, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("US$ 12345.68", amount.toString());
    }

    @Test
    public void testJPY() {
        Amount amount = new Amount(JPY, VALUE);
        assertEquals(JPY, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("¥ 12346", amount.toString());
    }

    @Test
    public void testGBP() {
        Amount amount = new Amount(GBP, VALUE);
        assertEquals(GBP, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("£ 12345.68", amount.toString());
    }

    @Test
    public void testEUR() {
        Amount amount = new Amount(EUR, VALUE);
        assertEquals(EUR, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("€ 12345.68", amount.toString());
    }

    @Test
    public void testSGD() {
        Amount amount = new Amount(SGD, VALUE);
        assertEquals(SGD, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("SGD 12345.68", amount.toString());
    }

    @Test
    public void testTWD() {
        Amount amount = new Amount(TWD, VALUE);
        assertEquals(TWD, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("$ 12345.68", amount.toString());
    }


    @Test
    public void testCAD() {
        Amount amount = new Amount(CAD, VALUE);
        assertEquals(CAD, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("CA$ 12345.68", amount.toString());
    }

    @Test
    public void testAUD() {
        Amount amount = new Amount(AUD, VALUE);
        assertEquals(AUD, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("AU$ 12345.68", amount.toString());
    }

    @Test
    public void testKRW() {
        Amount amount = new Amount(KRW, VALUE);
        assertEquals(KRW, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("￦ 12346", amount.toString());
    }

    @Test
    public void testILS() {
        Amount amount = new Amount(ILS, VALUE);
        assertEquals(ILS, amount.getCurrency());
        assertEquals(12345.67890, amount.getValue().doubleValue());
        assertEquals("₪ 12345.68", amount.toString());
    }

    @Test
    public void all() {
        List<Currency> sortedCurrencies = Currency.getAvailableCurrencies().stream()
                .sorted(Comparator.comparing(Currency::getCurrencyCode)).collect(Collectors.toList());
        for (Currency currency : sortedCurrencies) {
            Amount amount = new Amount(currency, VALUE);
            assertEquals(currency, amount.getCurrency());
            assertEquals(12345.67890, amount.getValue().doubleValue());
            logger.info(currency.getDisplayName(Locale.ENGLISH) + ": " + amount.toString());
        }
    }

    @Test
    public void add() {
        assertThrows(IllegalArgumentException.class, () -> ONE_USD.add(ONE_EUR));
        assertEquals(TWO_USD, ONE_USD.add(ONE_USD));
    }

    @Test
    public void subtract() {
        assertThrows(IllegalArgumentException.class, () -> TWO_USD.subtract(ONE_EUR));
        assertEquals(ONE_USD, TWO_USD.subtract(ONE_USD));
    }
}
