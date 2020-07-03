package com.leeym.common;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(ProfileIdTypeAdapter.class)
public class ProfileId extends NumericId {
    public ProfileId(String value) {
        super(value);
    }
}
