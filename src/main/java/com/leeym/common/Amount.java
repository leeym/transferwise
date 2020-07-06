package com.leeym.common;

import com.google.common.base.Preconditions;
import com.leeym.api.rates.Rate;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Objects;

import static java.math.RoundingMode.HALF_UP;

public class Amount implements Comparable<Amount> {
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
        Preconditions.checkArgument(currency.equals(that.currency), "Can't subtract " + that + " from " + this);
        return new Amount(currency, value.subtract(that.value));
    }

    public Amount multiply(BigDecimal multiplicand) {
        return new Amount(currency, value.multiply(multiplicand));
    }

    public Amount multiply(Rate rate) {
        Preconditions.checkArgument(currency.equals(rate.getSource()), "Can't multiply " + this + " by " + rate);
        return new Amount(rate.getTarget(), value.multiply(rate.getRate()));
    }

    public Amount divide(Rate rate) {
        Preconditions.checkArgument(currency.equals(rate.getTarget()), "Can't divide " + this + " by " + rate);
        return new Amount(rate.getSource(), value.divide(rate.getRate(), rate.getSource().getDefaultFractionDigits(),
                HALF_UP));
    }

    public Amount divide(BigDecimal divisor) {
        return new Amount(currency, value.divide(divisor, currency.getDefaultFractionDigits(), HALF_UP));
    }

    public double divide(Amount that) {
        Preconditions.checkArgument(currency.equals(that.currency), "Can't divide " + this + " by " + that);
        return value.doubleValue() / that.getValue().doubleValue();
    }

    public boolean isPositive() {
        return isGreaterThan(new Amount(currency, BigDecimal.ZERO));
    }

    public boolean isNegative() {
        return isLessThan(new Amount(currency, BigDecimal.ZERO));
    }

    public boolean isZero() {
        return equals(new Amount(currency, BigDecimal.ZERO));
    }

    public Amount abs() {
        return isNegative() ? negate() : this;
    }

    public Amount negate() {
        return new Amount(currency, value.negate());
    }

    public boolean isLessThan(Amount that) {
        Preconditions.checkArgument(currency.equals(that.currency), "can't compare " + this + " to " + that);
        return value.compareTo(that.value) < 0;
    }

    public boolean isGreaterThan(Amount that) {
        Preconditions.checkArgument(currency.equals(that.currency), "can't compare " + this + " to " + that);
        return value.compareTo(that.value) > 0;
    }

    @Override
    public String toString() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(currency.getDefaultFractionDigits());
        numberFormat.setMaximumFractionDigits(currency.getDefaultFractionDigits());
        return currency.getSymbol() + " " + numberFormat.format(value.doubleValue());
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
        if (!Objects.equals(currency, amount.currency)) {
            return false;
        }
        int scale = currency.getDefaultFractionDigits();
        return Objects.equals(value.setScale(scale, HALF_UP), amount.value.setScale(scale, HALF_UP));
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, value);
    }

    @Override
    public int compareTo(Amount that) {
        Preconditions.checkArgument(currency.equals(that.currency), "can't compare " + this + " to " + that);
        return this.value.compareTo(that.value);
    }
}
