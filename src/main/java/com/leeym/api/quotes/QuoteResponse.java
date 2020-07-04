package com.leeym.api.quotes;

import com.leeym.api.userprofiles.Profile;
import com.leeym.api.users.UserId;
import com.leeym.api.userprofiles.ProfileId;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Currency;

public class QuoteResponse {
    QuoteId id;
    Currency source;
    Currency target;
    BigDecimal sourceAmount;
    BigDecimal targetAmount;
    Type type;
    BigDecimal rate;
    OffsetDateTime createdTime;
    UserId createdByUserId;
    ProfileId profile;
    RateType rateType;
    OffsetDateTime deliveryEstimate;
    BigDecimal fee;
    FeeDetails feeDetails;
    Profile.Type[] allowedProfileTypes;
    Boolean guaranteedTargetAmount;
    Boolean ofSourceAmount;

    private static class FeeDetails {
        BigDecimal transferwise;
        BigDecimal payIn;
        BigDecimal discount;
        BigDecimal partner;
    }
}
