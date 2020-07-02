package com.leeym.api;

import com.leeym.common.CurrencyCode;
import com.leeym.common.SourceCurrencyCode;
import com.leeym.common.TargetCurrencyCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

class RatesAPITest extends BaseAPITest {

    private final RatesAPI api = new RatesAPI(Stage.SANDBOX, REAL_SANDBOX_API_TOKEN);

    @Disabled
    @Test
    public void testRate() {
        Rate rate = api.getRate(new SourceCurrencyCode("USD"), new TargetCurrencyCode("EUR"));
        Assertions.assertEquals("USD", rate.getSource());
        Assertions.assertEquals("EUR", rate.getTarget());
        System.err.println(rate);
    }

    @Disabled
    @Test
    public void testRates() {
        List<Rate> rates = api.getRates(new TargetCurrencyCode("USD"));
        Assertions.assertFalse(rates.isEmpty());
        for (Rate rate : rates) {
            Assertions.assertEquals("USD", rate.getTarget());
            System.err.println(rate);
        }
    }
}