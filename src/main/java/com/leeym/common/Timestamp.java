package com.leeym.common;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Timestamp extends FormattedString {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSX");
    private final OffsetDateTime offsetDateTime;

    public Timestamp(String value) {
        super(value);
        this.offsetDateTime = OffsetDateTime.parse(value, FORMATTER);
    }

    @Override
    String format() {
        return "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$";
    }

    public OffsetDateTime getOffsetDateTime() {
        return offsetDateTime;
    }

}
