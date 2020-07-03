package com.leeym.common;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class JavaTimeTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        switch (type.getRawType().getCanonicalName()) {
            case "java.time.LocalTime":
                return (TypeAdapter<T>) new LocalTimeTypeAdapter();
            case "java.time.LocalDate":
                return (TypeAdapter<T>) new LocalDateTypeAdapter();
            case "java.time.LocalDateTime":
                return (TypeAdapter<T>) new LocalDateTimeTypeAdapter();
            case "java.time.OffsetDateTime":
                return (TypeAdapter<T>) new OffsetDateTimeTypeAdapter();
            default:
                return null;
        }
    }
}
