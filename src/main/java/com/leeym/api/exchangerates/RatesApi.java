package com.leeym.api.exchangerates;

import com.leeym.api.BaseApi;
import com.leeym.api.Stage;
import com.leeym.common.ApiToken;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// https://api-docs.transferwise.com/#exchange-rates
public class RatesApi extends BaseApi {
    public RatesApi(Stage stage, ApiToken token) {
        super(stage, token);
    }

    public List<Rate> getRates(RatesRequest request) {
        Objects.requireNonNull(request);
        String query = request.toQueryString();
        String json = get("/v1/rates" + (query.isEmpty() ? "" : "?" + query));
        Rate[] rates = gson.fromJson(json, Rate[].class);
        return Arrays.stream(rates).collect(Collectors.toList());
    }
}
