package com.leeym.api.quotes;

import com.leeym.common.ProfileId;

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
    ProfileId profile;
    RateType rateType;
    OffsetDateTime deliveryEstimate;
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
