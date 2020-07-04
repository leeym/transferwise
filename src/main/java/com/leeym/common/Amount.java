package com.leeym.common;

import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

import static java.math.RoundingMode.HALF_UP;

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

    public Amount add(Amount that) {
        Preconditions.checkArgument(currency.equals(that.currency), "can't add " + that + " to " + this);
        return new Amount(currency, value.add(that.value));
    }

    public Amount subtract(Amount that) {
        Preconditions.checkArgument(currency.equals(that.currency), "Can't subtract " + that + " from " + that);
        return new Amount(currency, value.subtract(that.value));
    }

    @Override
    public String toString() {
        return currency.getCurrencyCode() + value.setScale(currency.getDefaultFractionDigits(), HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Amount amount = (Amount) o;
        return Objects.equals(currency, amount.currency) &&
                Objects.equals(value, amount.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, value);
    }
}
