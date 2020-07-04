package com.leeym.api.exchangerates;

import com.leeym.api.ApiRequest;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Currency;

public class RatesRequest extends ApiRequest {
    public Currency source;
    public Currency target;
    public OffsetDateTime time;
    public LocalDate from;
    public LocalDate to;
    public Interval group;

    RatesRequest(Currency source, Currency target) {
        this.source = source;
        this.target = target;
    }

    RatesRequest(Currency source, Currency target, OffsetDateTime time) {
        this(source, target);
        this.time = time;
    }

    RatesRequest(Currency source, Currency target, LocalDate from, LocalDate to, Interval group) {
        this(source, target);
        this.from = from;
        this.to = to;
        this.group = group;
    }

    @Override
    public String toString() {
        return "RatesRequest{" +
                "source=" + source +
                ", target=" + target +
                ", time='" + time + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", group=" + group +
                '}';
    }

    public enum Interval {
        day,
        hour,
        minute
    }
}
