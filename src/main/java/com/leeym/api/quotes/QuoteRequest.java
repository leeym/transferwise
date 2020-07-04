package com.leeym.api.quotes;

import com.leeym.common.ProfileId;

import java.math.BigDecimal;
import java.util.Currency;

public class QuoteRequest {
    String profile;
    Currency source;
    Currency target;
    RateType rateType;
    BigDecimal sourceAmount;
    BigDecimal targetAmount;
    Type type;

    private QuoteRequest(ProfileId profile, Currency source, Currency target, Type type) {
        this.profile = profile.toString();
        this.source = source;
        this.target = target;
        this.rateType = RateType.FIXED;
        this.type = type;
    }

    QuoteRequest(ProfileId profile, Currency source, BigDecimal sourceAmount, Currency target, Type type) {
        this(profile, source, target, type);
        this.sourceAmount = sourceAmount;
    }

    QuoteRequest(ProfileId profile, Currency source, Currency target, BigDecimal targetAmount, Type type) {
        this(profile, source, target, type);
        this.targetAmount = targetAmount;
    }

}
