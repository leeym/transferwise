package com.leeym.api;

public class Amount {
    private final Long value;
    private final String currency;

    public Amount(String currency, Long value) {
        this.currency = currency;
        this.value = value;
    }

    public Long getValue() {
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
