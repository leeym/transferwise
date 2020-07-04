package com.leeym.api;

import com.google.common.collect.Iterables;
import com.leeym.api.exchangerates.Rate;
import com.leeym.api.exchangerates.RatesApi;
import com.leeym.api.exchangerates.RatesRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.leeym.api.Currencies.EUR;
import static com.leeym.api.Currencies.USD;
import static com.leeym.api.Stage.SANDBOX;
import static com.leeym.api.exchangerates.RatesRequest.Interval.day;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RatesApiITCase extends BaseApiITCase {

    private final RatesApi api = new RatesApi(SANDBOX, SANDBOX_API_TOKEN);

    @Test
    public void testRate() {
        RatesRequest request = new RatesRequest(USD, EUR);
        Rate rate = Iterables.getOnlyElement(api.getRates(request));
        assertEquals(USD, rate.getSource());
        assertEquals(EUR, rate.getTarget());
        System.err.println(rate);
    }

    @Test
    public void testRatesGroupedByDate() {
        RatesRequest request =
                new RatesRequest(USD, EUR, LocalDate.parse("2020-06-01"), LocalDate.parse("2020-06-30"), day);
        List<Rate> rates = api.getRates(request);
        assertEquals(30, rates.size());
        for (Rate rate : rates) {
            assertEquals(USD, rate.getSource());
            assertEquals(EUR, rate.getTarget());
            System.err.println(rate);
        }
    }
}
