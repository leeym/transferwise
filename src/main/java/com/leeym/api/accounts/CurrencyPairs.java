package com.leeym.api.accounts;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.Optional;

public class CurrencyPairs {
    public SourceCurrency[] sourceCurrencies;
    public Integer total;

    public Optional<SourceCurrency> get(Currency currency) {
        return Arrays.stream(sourceCurrencies)
                .filter(s -> s.currencyCode.equals(currency))
                .findAny();
    }

    public static class SourceCurrency {
        public Currency currencyCode;
        public BigDecimal maxInvoiceAmount;
        public TargetCurrency[] targetCurrencies;
        public Integer totalTargetCurrencies;

        public Optional<TargetCurrency> get(Currency currency) {
            return Arrays.stream(targetCurrencies)
                    .filter(s -> s.currencyCode.equals(currency))
                    .findAny();
        }
    }

    public static class TargetCurrency {
        public Currency currencyCode;
        public BigDecimal minInvoiceAmount;
        public Boolean fixedTargetPaymentAllowed;
    }
}
