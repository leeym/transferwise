package com.leeym.api.userprofiles;

import com.leeym.api.BaseAPI;
import com.leeym.api.Stage;
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
        Profile[] profiles = gson.fromJson(json, Profile[].class);
        return Arrays.stream(profiles).collect(Collectors.toList());
    }

    public Profile getProfile(ProfileId profileId) {
        String json = get("/v1/profiles/" + profileId);
        return gson.fromJson(json, Profile.class);
    }
}
