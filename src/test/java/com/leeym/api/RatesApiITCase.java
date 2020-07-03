package com.leeym.api;

import com.google.common.collect.Iterables;
import com.leeym.api.exchangerates.Interval;
import com.leeym.api.exchangerates.Rate;
import com.leeym.api.exchangerates.RatesApi;
import com.leeym.api.exchangerates.RatesRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.List;

class RatesApiITCase extends BaseApiITCase {

    private final RatesApi api = new RatesApi(Stage.SANDBOX, REAL_SANDBOX_API_TOKEN);

    @Test
    public void testRate() {
        RatesRequest request = new RatesRequest(Currency.getInstance("USD"), Currency.getInstance("EUR"));
        Rate rate = Iterables.getOnlyElement(api.getRates(request));
        Assertions.assertEquals(Currency.getInstance("USD"), rate.getSource());
        Assertions.assertEquals(Currency.getInstance("EUR"), rate.getTarget());
        System.err.println(rate);
    }

    @Test
    public void testRatesGroupedByDate() {
        RatesRequest request =
                new RatesRequest(Currency.getInstance("USD"), Currency.getInstance("EUR"), "2020-06-01",
                        "2020-06-30", Interval.day);
        List<Rate> rates = api.getRates(request);
        Assertions.assertEquals(30, rates.size());
        for (Rate rate : rates) {
            Assertions.assertEquals(Currency.getInstance("USD"), rate.getSource());
            Assertions.assertEquals(Currency.getInstance("EUR"), rate.getTarget());
            System.err.println(rate);
        }
    }
}
