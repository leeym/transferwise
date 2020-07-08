package com.leeym.common;

import java.util.Objects;

public abstract class FormattedString {
    private final String value;

    public FormattedString(String value) {
        this.value = validate(value);
    }

    String format() {
        return ".+";
    }

    String validate(String string) {
        if (Objects.isNull(string)) {
            throw new NullPointerException(getClass().getSimpleName() + " can't be null");
        }
        string = string.trim();
        if (string.isEmpty()) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " can't be empty");
        }
        if (!string.matches(format())) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " [" + string +
                    "] doesn't match [" + format() + "]");
        }
        return string;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FormattedString that = (FormattedString) o;
        return this.value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
