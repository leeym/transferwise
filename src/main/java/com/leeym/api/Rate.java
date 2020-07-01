package com.leeym.api;

import java.math.BigDecimal;

// https://api-docs.transferwise.com/#exchange-rates
public class Rate {
    private BigDecimal rate;
    private String source;
    private String target;
    private String time;

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

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public String getTime() {
        return time;
    }
}
