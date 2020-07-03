package com.leeym.common;

import java.util.Objects;

public abstract class FormattedString {
    protected final String value;

    public FormattedString(String value) {
        if (Objects.isNull(value)) {
            throw new NullPointerException(getClass().getSimpleName() + " can't be null");
        }
        if (value.isEmpty()) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " can't be empty");
        }
        if (!value.matches(format())) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " [" + value +
                    "] doesn't match [" + format() + "]");
        }
        this.value = value;
    }

    abstract String format();

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
