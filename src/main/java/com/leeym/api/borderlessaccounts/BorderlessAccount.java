package com.leeym.api.borderlessaccounts;

import com.leeym.common.Amount;
import com.leeym.api.userprofiles.ProfileId;
import com.leeym.common.IBAN;
import com.leeym.common.Swift;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BorderlessAccount {
    private BorderlessAccountId id;
    private ProfileId profileId;
    private RecipientId recipientId;
    private OffsetDateTime creationTime;
    private OffsetDateTime modificationTime;
    private Boolean active;
    private Boolean eligible;
    private Balance[] balances;

    public List<Balance> getBalances() {
        return Arrays.stream(balances).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "BorderlessAccount{" +
                "id='" + id + '\'' +
                ", profileId='" + profileId + '\'' +
                ", recipientId='" + recipientId + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", modificationTime='" + modificationTime + '\'' +
                ", active=" + active +
                ", eligible=" + eligible +
                ", balances=" + Arrays.toString(balances) +
                '}';
    }

    enum BalanceType {
        AVAILABLE
    }

    public static class Balance {
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

        static class BankDetails {
            private BankDetailsId id;
            private Currency currency;
            private String bankCode;
            private String accountNumber;
            private Swift swift;
            private IBAN iban;
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
                    return Stream.of(addressFirstLine, addressSecondLine, city, stateCode, postCode, country)
                            .map(Object::toString)
                            .filter(string -> !string.isEmpty())
                            .collect(Collectors.joining(" "));
                }
            }
        }
    }

}
