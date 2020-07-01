package com.leeym.api;

import java.math.BigDecimal;

public class Amount {
    private final BigDecimal value;
    private final String currency;

    public Amount(String currency, BigDecimal value) {
        this.currency = currency;
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "value=" + value +
                ", currency='" + currency + '\'' +
                '}';
    }
}
