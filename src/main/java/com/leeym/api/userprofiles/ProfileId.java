package com.leeym.api.userprofiles;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.leeym.common.NumberId;

import java.io.IOException;

@JsonAdapter(ProfileId.TypeAdapter.class)
public class ProfileId extends NumberId {
    public ProfileId(String value) {
        super(value);
    }

    static class TypeAdapter extends com.google.gson.TypeAdapter<ProfileId> {

        @Override
        public void write(JsonWriter out, ProfileId value) throws IOException {
            out.value(value.toString());
        }

        @Override
        public ProfileId read(JsonReader in) throws IOException {
            return new ProfileId(in.nextString());
        }
    }
}
