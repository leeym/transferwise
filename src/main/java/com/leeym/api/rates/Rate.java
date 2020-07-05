package com.leeym.api.rates;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Currency;

// https://api-docs.transferwise.com/#exchange-rates
public class Rate {
    private final BigDecimal rate;
    private final Currency source;
    private final Currency target;
    private final OffsetDateTime time;

    public Rate(Currency source, Currency target, double rate) {
        this(source, target, new BigDecimal(rate), OffsetDateTime.now());
    }

    public Rate(Currency source, Currency target, double rate, OffsetDateTime time) {
        this(source, target, new BigDecimal(rate), time);
    }

    public Rate(Currency source, Currency target, BigDecimal rate) {
        this(source, target, rate, OffsetDateTime.now());
    }

    public Rate(Currency source, Currency target, BigDecimal rate, OffsetDateTime time) {
        this.source = source;
        this.target = target;
        this.rate = rate;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "rate=" + rate +
                ", source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public BigDecimal getRate() {
        return rate;
    }

    public Currency getSource() {
        return source;
    }

    public Currency getTarget() {
        return target;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public Rate reverse() {
        return new Rate(target, source, 1 / rate.doubleValue(), time);
    }
}
