package com.leeym.api;

import java.math.BigDecimal;

public class CurrencyPairs {
    SourceCurrencies[] sourceCurrencies;
    Integer total;

    public static class SourceCurrencies {
        String currencyCode;
        BigDecimal maxInvoiceAmount;
        TargetCurrencies[] targetCurrencies;
        Integer totalTargetCurrencies;
    }

    public static class TargetCurrencies {
        String currencyCode;
        BigDecimal minInvoiceAmount;
        Boolean fixedTargetPaymentAllowed;
    }
}
