package com.leeym.api.accounts;

import java.util.Currency;

public class BalanceCurrency {
    Currency code;
    Boolean hasBankDetails;
    Boolean payInAllowed;
    Account.Balance.BankDetails sampleBankDetails;

    public Currency getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "BalanceCurrency{" +
                "code=" + code +
                ", hasBankDetails=" + hasBankDetails +
                ", payInAllowed=" + payInAllowed +
                ", sampleBankDetails=" + sampleBankDetails +
                '}';
    }
}
