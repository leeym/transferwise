package com.leeym.api.borderlessaccounts;

import java.util.Currency;
import java.util.Locale;

public class BankDetails {
    private String id;
    private Currency currency;
    private String bankCode;
    private String accountNumber;
    private String swift;
    private String iban;
    private String bankName;
    private String accountHolderName;
    private Address bankAddress;

    @Override
    public String toString() {
        return "BankDetails{" +
                "id='" + id + '\'' +
                ", currency='" + currency + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", swift='" + swift + '\'' +
                ", iban='" + iban + '\'' +
                ", bankName='" + bankName + '\'' +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", bankAddress=" + bankAddress +
                '}';
    }

    static class Address {
        private String addressFirstLine;
        private String addressSecondLine;
        private String postCode;
        private String city;
        private Locale.IsoCountryCode country;
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
}
