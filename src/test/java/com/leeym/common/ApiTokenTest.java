package com.leeym.common;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.leeym.api.Stage.SANDBOX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApiTokenTest {

    @Test
    public void testNull() {
        Exception e = assertThrows(NullPointerException.class, () -> new ApiToken(null, SANDBOX));
        assertEquals("ApiToken can't be null", e.getMessage());
    }

    @Test
    public void testEmpty() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new ApiToken("", SANDBOX));
        assertEquals("ApiToken can't be empty", e.getMessage());
    }

    @Test
    public void testInvalid() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new ApiToken("foobar", SANDBOX));
        assertEquals("ApiToken [foobar] doesn't match [^\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}$]",
                e.getMessage());
    }

    @Test
    public void testValid() {
        UUID uuid = UUID.randomUUID();
        assertEquals(uuid.toString(), new ApiToken(uuid.toString(), SANDBOX).toString());
    }
}
