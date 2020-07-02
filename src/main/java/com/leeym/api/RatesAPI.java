package com.leeym.api;

import com.google.gson.Gson;
import com.leeym.common.APIToken;
import com.leeym.common.SourceCurrencyCode;
import com.leeym.common.TargetCurrencyCode;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// https://api-docs.transferwise.com/#exchange-rates
public class RatesAPI extends BaseAPI {
    public RatesAPI(Stage stage, APIToken token) {
        super(stage, token);
    }

    public List<Rate> getRates(SourceCurrencyCode source) {
        Objects.requireNonNull(source);
        String json = get("/v1/rates?source=" + source);
        Rate[] rates = new Gson().fromJson(json, Rate[].class);
        return Arrays.stream(rates).collect(Collectors.toList());
    }

    public List<Rate> getRates(TargetCurrencyCode target) {
        Objects.requireNonNull(target);
        String json = get("/v1/rates?target=" + target);
        Rate[] rates = new Gson().fromJson(json, Rate[].class);
        return Arrays.stream(rates).collect(Collectors.toList());
    }

    public Rate getRate(SourceCurrencyCode source, TargetCurrencyCode target) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(target);
        String json = get("/v1/rates?source=" + source + "&target=" + target);
        Rate[] rates = new Gson().fromJson(json, Rate[].class);
        return new Gson().fromJson(json, Rate[].class)[0];
    }
}
