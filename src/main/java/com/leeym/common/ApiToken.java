package com.leeym.common;

public class ApiToken extends UUID {
    private final Stage stage;

    public ApiToken(String value, Stage stage) {
        super(value);
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
