package com.leeym.common;

import static com.leeym.common.Stage.LIVE;
import static com.leeym.common.Stage.SANDBOX;

public class BaseUrl extends FormattedString {
    public static final BaseUrl SANDBOX_BASEURL = new BaseUrl("https://api.sandbox.transferwise.tech", SANDBOX);
    public static final BaseUrl LIVE_BASEURL = new BaseUrl("https://api.transferwise.com", LIVE);

    Stage stage;

    public BaseUrl(String value, Stage stage) {
        super(value);
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public String format() {
        return "^https://api.(sandbox.transferwise.tech|transferwise.com)$";
    }
}
