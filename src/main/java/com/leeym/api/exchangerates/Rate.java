package com.leeym.api.exchangerates;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Currency;

// https://api-docs.transferwise.com/#exchange-rates
public class Rate {
    private BigDecimal rate;
    private Currency source;
    private Currency target;
    private OffsetDateTime time;

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
}
