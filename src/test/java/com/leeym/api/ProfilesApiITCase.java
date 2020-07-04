package com.leeym.api;

import com.leeym.api.userprofiles.Profile;
import com.leeym.api.userprofiles.ProfilesApi;
import com.leeym.common.ProfileId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.leeym.api.userprofiles.Profile.Type.business;
import static com.leeym.api.userprofiles.Profile.Type.personal;
import static com.leeym.common.BaseUrl.SANDBOX_BASEURL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProfilesApiITCase extends BaseApiITCase {

    private final ProfilesApi api = new ProfilesApi(SANDBOX_BASEURL, SANDBOX_API_TOKEN);

    @Test
    public void testProfile() {
        assertProfile(api.getProfile(SANDBOX_PERSONAL_PROFILE_ID), SANDBOX_PERSONAL_PROFILE_ID, personal);
    }

    @Test
    public void testProfiles() {
        List<Profile> profiles = api.getProfiles();
        assertEquals(2, profiles.size());
        assertProfile(profiles.get(0), SANDBOX_PERSONAL_PROFILE_ID, personal);
        assertProfile(profiles.get(1), SANDBOX_BUSINESS_PROFILE_ID, business);
    }

    private void assertProfile(Profile profile, ProfileId id, Profile.Type type) {
        assertNotNull(profile);
        assertEquals(id, profile.getId());
        assertEquals(type, profile.getType());
    }
}
