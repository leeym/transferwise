package com.leeym.common;

public abstract class UUID extends FormattedString {
    public UUID(String value) {
        super(value);
    }

    @Override
    public String format() {
        return "^\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}$";
    }
}
