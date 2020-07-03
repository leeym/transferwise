package com.leeym.api;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class APIRequest {
    public String toQueryString() {
        StringBuilder sb = new StringBuilder();
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!Modifier.isPublic(field.getModifiers())) continue;
            Object value;
            try {
                value = field.get(this);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (value == null) continue;
            if (sb.length() > 0) sb.append('&');
            sb.append(field.getName()).append('=').append(value);
        }
        return sb.toString();
    }
}
