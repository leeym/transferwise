package com.leeym.api.rates;

import com.google.common.collect.Iterables;
import com.leeym.api.BaseApi;
import com.leeym.common.ApiToken;
import com.leeym.common.BaseUrl;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// https://api-docs.transferwise.com/#exchange-rates
public class RatesApi extends BaseApi {
    public RatesApi(BaseUrl baseUrl, ApiToken token) {
        super(baseUrl, token);
    }

    protected List<Rate> getRates(RatesRequest request) {
        Objects.requireNonNull(request);
        String query = request.toQueryString();
        String json = get("/v1/rates" + (query.isEmpty() ? "" : "?" + query));
        Rate[] rates = gson.fromJson(json, Rate[].class);
        return Arrays.stream(rates).collect(Collectors.toList());
    }

    public Rate getRateNow(Currency source, Currency target) {
        RatesRequest request = new RatesRequest(source, target);
        return Iterables.getOnlyElement(getRates(request));
    }

    public List<Rate> getSourceRatesNow(Currency source) {
        RatesRequest request = new RatesRequest(source, null);
        return getRates(request);
    }

    public List<Rate> getTargetRatesNow(Currency target) {
        RatesRequest request = new RatesRequest(null, target);
        return getRates(request);
    }

    public Rate getRateAt(Currency source, Currency target, OffsetDateTime offsetDateTime) {
        RatesRequest request = new RatesRequest(source, target, offsetDateTime);
        return Iterables.getOnlyElement(getRates(request));
    }

    public List<Rate> getDailyRatesBetween(Currency source, Currency target, LocalDate from, LocalDate to) {
        RatesRequest request = new RatesRequest(source, target, from, to, RatesRequest.Interval.day);
        return getRates(request);
    }
}
