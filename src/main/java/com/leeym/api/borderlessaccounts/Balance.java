package com.leeym.api.borderlessaccounts;

import java.util.Currency;

public class Balance {
    private Amount amount;
    private Amount reservedAmount;
    private BankDetails bankDetails;
    private BalanceType balanceType;
    private Currency currency;

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
