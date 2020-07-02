package com.leeym.api.exchangerates;

import com.google.gson.Gson;
import com.leeym.api.BaseAPI;
import com.leeym.api.Stage;
import com.leeym.common.APIToken;

import java.util.Currency;
import java.util.Objects;

// https://api-docs.transferwise.com/#exchange-rates
public class RatesAPI extends BaseAPI {
    public RatesAPI(Stage stage, APIToken token) {
        super(stage, token);
    }

    public Rate getRate(Currency source, Currency target) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(target);
        String json = get("/v1/rates?source=" + source.getCurrencyCode() + "&target=" + target.getCurrencyCode());
        Rate[] rates = new Gson().fromJson(json, Rate[].class);
        return new Gson().fromJson(json, Rate[].class)[0];
    }
}
