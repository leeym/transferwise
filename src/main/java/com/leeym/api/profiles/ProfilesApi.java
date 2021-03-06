package com.leeym.api.profiles;

import com.leeym.api.BaseApi;
import com.leeym.common.ApiToken;
import com.leeym.common.BaseUrl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// https://api-docs.transferwise.com/#user-profiles
public class ProfilesApi extends BaseApi {

    public ProfilesApi(BaseUrl baseUrl, ApiToken token) {
        super(baseUrl, token);
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
