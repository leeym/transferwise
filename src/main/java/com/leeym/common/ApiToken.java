package com.leeym.common;

import com.leeym.api.Stage;

public class ApiToken extends UUIDString {
    private final Stage stage;

    public ApiToken(String value, Stage stage) {
        super(value);
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
