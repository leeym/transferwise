package com.leeym.api;

public class Rate {
    private Long rate;
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

    public Long getRate() {
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
