package com.leeym.common;

import com.leeym.api.Stage;

public class ProfileId extends FormattedString {
    private final Type type;
    private final Stage stage;

    public ProfileId(String value, Stage stage, Type type) {
        super(value);
        this.stage = stage;
        this.type = type;
    }

    @Override
    String format() {
        return "^\\d+$";
    }

    public Stage getStage() {
        return stage;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        PERSONAL,
        BUSINESS,
    }
}
