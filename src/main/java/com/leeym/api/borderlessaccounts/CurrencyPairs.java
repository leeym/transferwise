package com.leeym.api.borderlessaccounts;

import java.math.BigDecimal;
import java.util.Currency;

public class CurrencyPairs {
    public SourceCurrency[] sourceCurrencies;
    public Integer total;

    public static class SourceCurrency {
        public Currency currencyCode;
        public BigDecimal maxInvoiceAmount;
        public TargetCurrency[] targetCurrencies;
        public Integer totalTargetCurrencies;
    }

    public static class TargetCurrency {
        public Currency currencyCode;
        public BigDecimal minInvoiceAmount;
        public Boolean fixedTargetPaymentAllowed;
    }
}
