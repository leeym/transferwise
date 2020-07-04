package com.leeym.api;

import com.leeym.api.exchangerates.Rate;
import com.leeym.api.exchangerates.RatesApi;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.leeym.api.Currencies.EUR;
import static com.leeym.api.Currencies.USD;
import static com.leeym.api.Stage.SANDBOX;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RatesApiITCase extends BaseApiITCase {

    private final RatesApi api = new RatesApi(SANDBOX, SANDBOX_API_TOKEN);

    @Test
    public void getRateNow() {
        Rate rate = api.getRateNow(USD, EUR);
        assertEquals(USD, rate.getSource());
        assertEquals(EUR, rate.getTarget());
    }

    @Test
    public void getDailyRatesBetween() {
        LocalDate firstDay = LocalDate.parse("2020-06-01");
        LocalDate lastDay = LocalDate.parse("2020-06-30");
        List<Rate> rates = api.getDailyRatesBetween(USD, EUR, firstDay, lastDay);
        assertEquals(30, rates.size());
        for (Rate rate : rates) {
            assertEquals(USD, rate.getSource());
            assertEquals(EUR, rate.getTarget());
        }
    }

    @Test
    public void getRateAt() {
        Rate rate = api.getRateAt(USD, EUR, OffsetDateTime.now(ZoneId.of("UTC")));
        assertEquals(USD, rate.getSource());
        assertEquals(EUR, rate.getTarget());
    }
}
