package com.leeym.common;

public abstract class UUIDString extends FormattedString {
    public UUIDString(String value) {
        super(value);
    }

    @Override
    String format() {
        return "^\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}$";
    }
}
