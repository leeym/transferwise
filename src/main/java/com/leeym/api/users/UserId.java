package com.leeym.api.users;

import com.google.gson.annotations.JsonAdapter;
import com.leeym.common.NumericId;

@JsonAdapter(UserIdTypeAdapter.class)
public class UserId extends NumericId {
    public UserId(String value) {
        super(value);
    }
}
