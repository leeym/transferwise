package com.leeym.common;

import com.leeym.api.Stage;

public class APIToken extends UUIDString {
    private final Stage stage;

    public APIToken(String value, Stage stage) {
        super(value);
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
