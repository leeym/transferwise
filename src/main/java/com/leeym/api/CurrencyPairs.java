package com.leeym.api;

import java.math.BigDecimal;

public class CurrencyPairs {
    public SourceCurrency[] sourceCurrencies;
    public Integer total;

    public static class SourceCurrency {
        public String currencyCode;
        public BigDecimal maxInvoiceAmount;
        public TargetCurrency[] targetCurrencies;
        public Integer totalTargetCurrencies;
    }

    public static class TargetCurrency {
        public String currencyCode;
        public BigDecimal minInvoiceAmount;
        public Boolean fixedTargetPaymentAllowed;
    }
}
