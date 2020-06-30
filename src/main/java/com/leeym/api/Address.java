package com.leeym.api;

public class Address {
    private String addressFirstLine;
    private String addressSecondLine;
    private String postCode;
    private String city;
    private String country;
    private String stateCode;

    @Override
    public String toString() {
        return "Address{" +
                "addressFirstLine='" + addressFirstLine + '\'' +
                ", addressSecondLine='" + addressSecondLine + '\'' +
                ", postCode='" + postCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", stateCode='" + stateCode + '\'' +
                '}';
    }
}
