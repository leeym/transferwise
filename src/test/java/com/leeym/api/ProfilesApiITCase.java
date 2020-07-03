package com.leeym.api;

import com.leeym.api.userprofiles.Profile;
import com.leeym.api.userprofiles.ProfilesAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ProfilesApiITCase extends BaseApiITCase {

    private final ProfilesAPI api = new ProfilesAPI(Stage.SANDBOX, REAL_SANDBOX_API_TOKEN);

    @Test
    public void testProfile() {
        Profile profile = api.getProfile(REAL_SANDBOX_PERSONAL_PROFILE_ID);
        Assertions.assertEquals(REAL_SANDBOX_PERSONAL_PROFILE_ID.toString(), profile.getId());
    }

    @Test
    public void testProfiles() {
        List<Profile> profiles = api.getProfiles();
        Assertions.assertEquals(2, profiles.size());
        Assertions.assertEquals(REAL_SANDBOX_PERSONAL_PROFILE_ID.toString(), profiles.get(0).getId());
        Assertions.assertEquals(REAL_SANDBOX_BUSINESS_PROFILE_ID.toString(), profiles.get(1).getId());
    }
}
