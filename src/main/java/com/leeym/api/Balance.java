package com.leeym.api;

public class Balance {
    private Amount amount;
    private Amount reservedAmount;
    private BankDetails bankDetails;
    private String balanceType;
    private String currency;

    public Amount getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "balanceType='" + balanceType + '\'' +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                ", reservedAmount=" + reservedAmount +
                ", bankDetails=" + bankDetails +
                '}';
    }
}
