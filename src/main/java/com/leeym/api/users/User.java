package com.leeym.api.users;

import com.leeym.common.Email;
import com.leeym.common.PhoneNumber;

import java.time.LocalDate;
import java.util.Locale;

public class User {
    UserId id;
    String name;
    Email email;
    Boolean active;
    Details details;
    LocalDate dateOfBirth;
    String avatar;
    Integer primaryAddress;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                ", details=" + details +
                ", dateOfBirth=" + dateOfBirth +
                ", avatar='" + avatar + '\'' +
                ", primaryAddress=" + primaryAddress +
                '}';
    }

    private static class Details {
        String firstName;
        String lastName;
        PhoneNumber phoneNumber;
        String occupation;
        Address address;

        @Override
        public String toString() {
            return "Details{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    ", occupation='" + occupation + '\'' +
                    ", address=" + address +
                    '}';
        }

        private static class Address {
            String city;
            Locale.IsoCountryCode countryCode;
            String postCode;
            String state;
            String firstLine;

            @Override
            public String toString() {
                return "Address{" +
                        "city='" + city + '\'' +
                        ", countryCode='" + countryCode + '\'' +
                        ", postCode='" + postCode + '\'' +
                        ", state='" + state + '\'' +
                        ", firstLine='" + firstLine + '\'' +
                        '}';
            }
        }
    }
}
