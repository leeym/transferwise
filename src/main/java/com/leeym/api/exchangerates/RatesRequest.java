package com.leeym.api.exchangerates;

import com.leeym.api.APIRequest;

import java.util.Currency;

public class RatesRequest extends APIRequest {
    public Currency source;
    public Currency target;
    public String time;
    public String from;
    public String to;
    public Interval group;

    public RatesRequest(Currency source, Currency target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return "RatesRequest{" +
                "source=" + source +
                ", target=" + target +
                ", time='" + time + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", group=" + group +
                '}';
    }
}
