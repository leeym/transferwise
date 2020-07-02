package com.leeym.api;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class Amount {
    private final String currency;
    private final BigDecimal value;

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
        try {
            return "Amount{" +
                    "currency='" + this.currency + '\'' +
                    ", value=" + value.setScale(Currency.getInstance(currency).getDefaultFractionDigits(), RoundingMode.HALF_UP) +
                    '}';
        } catch (IllegalArgumentException e) {
            System.err.println(currency + " not found");
            return "Amount{" +
                    "currency='" + currency + '\'' +
                    ", value=" + value +
                    '}';

        }
    }
}
