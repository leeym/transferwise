package com.leeym.api;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class Amount {
    private final Currency currency;
    private final BigDecimal value;

    public Amount(Currency currency, BigDecimal value) {
        this.currency = currency;
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "currency='" + this.currency.getCurrencyCode() + '\'' +
                ", value=" + value.setScale(currency.getDefaultFractionDigits(), RoundingMode.HALF_UP) +
                '}';
    }
}
