package com.leeym.common;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(RecipientIdTypeAdapter.class)
public class RecipientId extends NumericId {
    public RecipientId(String value) {
        super(value);
    }
}
