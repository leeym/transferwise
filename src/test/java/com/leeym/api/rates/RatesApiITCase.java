package com.leeym.api.rates;

import com.leeym.api.BaseApiITCase;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.leeym.api.Currencies.EUR;
import static com.leeym.api.Currencies.USD;
import static com.leeym.common.BaseUrl.SANDBOX_BASEURL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RatesApiITCase extends BaseApiITCase {

    private final RatesApi api = new RatesApi(SANDBOX_BASEURL, SANDBOX_API_TOKEN);

    @Test
    public void getRateNow() {
        Rate rate = api.getRateNow(USD, EUR);
        assertEquals(USD, rate.getSource());
        assertEquals(EUR, rate.getTarget());
    }

    @Test
    public void getSourceRatesNow() {
        List<Rate> rates = api.getSourceRatesNow(USD);
        assertFalse(rates.isEmpty());
        for (Rate rate : rates) {
            assertEquals(USD, rate.getSource());
            assertNotEquals(USD, rate.getTarget());
        }
    }

    @Test
    public void getTargetRatesNow() {
        List<Rate> rates = api.getTargetRatesNow(USD);
        assertFalse(rates.isEmpty());
        for (Rate rate : rates) {
            assertNotEquals(USD, rate.getSource());
            assertEquals(USD, rate.getTarget());
        }
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
