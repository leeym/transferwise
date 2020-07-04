package com.leeym.api.userprofiles;

import com.leeym.common.PhoneNumber;

import java.time.LocalDate;

public class Profile {
    private ProfileId id;
    private Type type;
    private Details details;

    public ProfileId getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public Details getDetails() {
        return details;
    }

    public enum Type {
        personal,
        business,
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", details=" + details +
                '}';
    }

    private static class Details {
        private String firstName;
        private String lastName;
        private LocalDate dateOfBirth;
        private PhoneNumber phoneNumber;
        private String avatar;
        private String occupation;
        private String primaryAddress;
        private String name;
        private String registrationNumber;
        private String acn;
        private String abn;
        private String abrn;
        private String companyType;
        private String companyRole;
        private String descriptionOfBusiness;
        private String webpage;
        private String businessCategory;
        private String businessSubCategory;

        public String getAbn() {
            return abn;
        }

        public String getAbrn() {
            return abrn;
        }

        public String getAcn() {
            return acn;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getBusinessCategory() {
            return businessCategory;
        }

        public String getBusinessSubCategory() {
            return businessSubCategory;
        }

        public String getCompanyRole() {
            return companyRole;
        }

        public String getCompanyType() {
            return companyType;
        }

        public LocalDate getDateOfBirth() {
            return dateOfBirth;
        }

        public String getDescriptionOfBusiness() {
            return descriptionOfBusiness;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getName() {
            return name;
        }

        public String getOccupation() {
            return occupation;
        }

        public PhoneNumber getPhoneNumber() {
            return phoneNumber;
        }

        public String getPrimaryAddress() {
            return primaryAddress;
        }

        public String getRegistrationNumber() {
            return registrationNumber;
        }

        public String getWebpage() {
            return webpage;
        }

        @Override
        public String toString() {
            return "Details{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", dateOfBirth='" + dateOfBirth + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", occupation='" + occupation + '\'' +
                    ", primaryAddress='" + primaryAddress + '\'' +
                    ", name='" + name + '\'' +
                    ", registrationNumber='" + registrationNumber + '\'' +
                    ", acn='" + acn + '\'' +
                    ", abn='" + abn + '\'' +
                    ", abrn='" + abrn + '\'' +
                    ", companyType='" + companyType + '\'' +
                    ", companyRole='" + companyRole + '\'' +
                    ", descriptionOfBusiness='" + descriptionOfBusiness + '\'' +
                    ", webpage='" + webpage + '\'' +
                    ", businessCategory='" + businessCategory + '\'' +
                    ", businessSubCategory='" + businessSubCategory + '\'' +
                    '}';
        }
    }
}
