package com.leeym.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.leeym.api.Stage.SANDBOX;

public class APITokenTest {

    @Test
    public void testNull() {
        Exception e = Assertions.assertThrows(NullPointerException.class, () -> new APIToken(null, SANDBOX));
        Assertions.assertEquals("APIToken can't be null", e.getMessage());
    }

    @Test
    public void testEmpty() {
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new APIToken("", SANDBOX));
        Assertions.assertEquals("APIToken can't be empty", e.getMessage());
    }

    @Test
    public void testInvalid() {
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new APIToken("foobar", SANDBOX));
        Assertions.assertEquals("APIToken [foobar] doesn't match [^\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}$]", e.getMessage());
    }

    @Test
    public void testValid() {
        UUID uuid = UUID.randomUUID();
        Assertions.assertEquals(uuid.toString(), new APIToken(uuid.toString(), SANDBOX).toString());
    }
}