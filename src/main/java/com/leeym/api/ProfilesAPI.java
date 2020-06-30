package com.leeym.api;

import com.google.gson.Gson;
import com.leeym.common.APIToken;
import com.leeym.common.ProfileId;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// https://api-docs.transferwise.com/#user-profiles
public class ProfilesAPI extends BaseAPI {

    public ProfilesAPI(Stage stage, APIToken token) {
        super(stage, token);
    }

    // https://api-docs.transferwise.com/#user-profiles-list
    public List<Profile> getProfiles() {
        String json = get("/v1/profiles");
        Profile[] profiles = new Gson().fromJson(json, Profile[].class);
        return Arrays.stream(profiles).collect(Collectors.toList());
    }

    public Profile getProfile(ProfileId profileId) {
        String json = get("/v1/profiles/" + profileId);
        return new Gson().fromJson(json, Profile.class);
    }
}
