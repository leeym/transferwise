package com.leeym.common;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PhoneNumberTest {
    private List<String> generate(String countryCode, String areaCode, List<String> phoneNumbers) {
        List<String> list = new LinkedList<>();
        List<String> delimiters = Arrays.asList("", " ", "-", ".");
        for (String s1 : Collections.singletonList(countryCode)) {
            for (String s2 : delimiters) {
                for (String s3 : Arrays.asList(areaCode, "(" + areaCode + ")")) {
                    for (String s4 : delimiters) {
                        for (String s5 : delimiters) {
                            list.add("+" + s1 + s2 + s3 + s4 + String.join(s5, phoneNumbers));
                        }
                    }
                }
            }
        }
        return list;
    }

    @Test
    public void invalid() {
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("886227721165"));
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("16504636237"));
    }

    @Test
    public void us() {
        for (String value : generate("1", "650", Arrays.asList("463", "6237"))) {
            assertEquals("+16504636237", new PhoneNumber(value).toString());
        }
    }

    @Test
    public void taiwan() {
        for (String value : generate("886", "2", Arrays.asList("2772", "1165"))) {
            assertEquals("+886227721165", new PhoneNumber(value).toString());
        }
    }

    // https://transferwise.com/help/24/technical-issues/2235393/customer-complaints-procedure#UK
    @Test
    public void uk() {
        for (String value : generate("44", "20", Arrays.asList("7964", "0500"))) {
            assertEquals("+442079640500", new PhoneNumber(value).toString());
        }
    }

    // https://transferwise.com/help/24/technical-issues/2235393/customer-complaints-procedure#Japan
    @Test
    public void japan() {
        for (String value : generate("+81", "03", Arrays.asList("3556", "6261"))) {
            assertEquals("+81335566261", new PhoneNumber(value).toString());
        }
    }

    // https://transferwise.com/help/24/technical-issues/2235393/customer-complaints-procedure#Belgium
    @Test
    public void belgium() {
        for (String value : generate("+32", "2", Arrays.asList("545", "77", "79"))) {
            assertEquals("+3225457779", new PhoneNumber(value).toString());
        }
    }
}
