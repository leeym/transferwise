package com.leeym.api;

import com.leeym.api.exchangerates.Rate;
import com.leeym.api.exchangerates.RatesAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Currency;

class RatesAPITest extends BaseAPITest {

    private final RatesAPI api = new RatesAPI(Stage.SANDBOX, REAL_SANDBOX_API_TOKEN);

    @Disabled
    @Test
    public void testRate() {
        Rate rate = api.getRate(Currency.getInstance("USD"), Currency.getInstance("EUR"));
        Assertions.assertEquals("USD", rate.getSource());
        Assertions.assertEquals("EUR", rate.getTarget());
        System.err.println(rate);
    }
}