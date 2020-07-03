package com.leeym.api.users;

import java.time.LocalDate;

/**
 * {
 * "id": 101,
 * "name": "Example Person",
 * "email": "person@example.com",
 * "active": true,
 * "details": {
 * "firstName": "Example",
 * "lastName": "Person",
 * "phoneNumber": "+37111111111",
 * "occupation": "",
 * "address": {
 * "city": "Tallinn",
 * "countryCode": "EE",
 * "postCode": "11111",
 * "state": "",
 * "firstLine": "Road 123"
 * },
 * "dateOfBirth": "1977-01-01",
 * "avatar": "https://lh6.googleusercontent.com/photo.jpg",
 * "primaryAddress": 111
 * }
 * }
 */
public class User {
    String id;
    String name;
    String email;
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
        String phoneNumber;
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
            String countryCode;
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
