package com.leeym.api.borderlessaccounts;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

public class CurrencyPairs {
    public SourceCurrency[] sourceCurrencies;
    public Integer total;

    public List<SourceCurrency> getSourceCurrencies() {
        return Arrays.stream(sourceCurrencies).collect(Collectors.toList());
    }

    public SourceCurrency getSourceCurrency(Currency currency) {
        return Arrays.stream(sourceCurrencies).filter(s -> s.currencyCode.equals(currency)).findFirst().get();
    }

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
