package com.leeym.api;

import com.leeym.api.userprofiles.Profile;
import com.leeym.api.userprofiles.ProfilesApi;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.leeym.api.Stage.SANDBOX;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfilesApiITCase extends BaseApiITCase {

    private final ProfilesApi api = new ProfilesApi(SANDBOX, REAL_SANDBOX_API_TOKEN);

    @Test
    public void testProfile() {
        Profile profile = api.getProfile(REAL_SANDBOX_PERSONAL_PROFILE_ID);
        assertEquals(REAL_SANDBOX_PERSONAL_PROFILE_ID.toString(), profile.getId());
    }

    @Test
    public void testProfiles() {
        List<Profile> profiles = api.getProfiles();
        assertEquals(2, profiles.size());
        assertEquals(REAL_SANDBOX_PERSONAL_PROFILE_ID.toString(), profiles.get(0).getId());
        assertEquals(REAL_SANDBOX_BUSINESS_PROFILE_ID.toString(), profiles.get(1).getId());
    }
}
