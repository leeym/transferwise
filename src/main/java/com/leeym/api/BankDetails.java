package com.leeym.api;

public class BankDetails {
    private String id;
    private String currency;
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
}
