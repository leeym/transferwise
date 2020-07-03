package com.leeym.api.quotes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Currency;

public class QuoteResponse {
    String id;
    Currency source;
    Currency target;
    BigDecimal sourceAmount;
    BigDecimal targetAmount;
    Type type;
    BigDecimal rate;
    OffsetDateTime createdTime;
    String createdByUserId;
    String profile;
    RateType rateType;
    String deliveryEstimate;
    BigDecimal fee;
    FeeDetails feeDetails;
    String[] allowedProfileTypes;
    Boolean guaranteedTargetAmount;
    Boolean ofSourceAmount;

    private static class FeeDetails {
        BigDecimal transferwise;
        BigDecimal payIn;
        BigDecimal discount;
        BigDecimal partner;
    }
}
