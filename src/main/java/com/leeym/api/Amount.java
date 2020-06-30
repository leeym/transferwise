package com.leeym.api;

public class Amount {
    private Long value;
    private String currency;

    @Override
    public String toString() {
        return "Amount{" +
                "value=" + value +
                ", currency='" + currency + '\'' +
                '}';
    }
}
