package com.leeym.api.exchangerates;

import org.junit.jupiter.api.Test;

import static com.leeym.api.Currencies.EUR;
import static com.leeym.api.Currencies.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RateTest {

    @Test
    public void reverse() {
        Rate rate = new Rate(USD, EUR, 0.889284917);
        assertEquals(USD, rate.getSource());
        assertEquals(EUR, rate.getTarget());
        assertEquals(0.889284917, rate.getRate().doubleValue());

        Rate reversed = rate.reverse();
        assertEquals(EUR, reversed.getSource());
        assertEquals(USD, reversed.getTarget());
        assertEquals(1.1244990001331598, reversed.getRate().doubleValue());
    }
}
