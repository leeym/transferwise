package com.leeym.api.quotes;

import com.leeym.api.userprofiles.Profile;
import com.leeym.api.userprofiles.ProfileId;
import com.leeym.api.users.UserId;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
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

    public QuoteId getId() {
        return id;
    }

    @Override
    public String toString() {
        return "QuoteResponse{" +
                "id=" + id +
                ", source=" + source +
                ", target=" + target +
                ", sourceAmount=" + sourceAmount +
                ", targetAmount=" + targetAmount +
                ", type=" + type +
                ", rate=" + rate +
                ", createdTime=" + createdTime +
                ", createdByUserId=" + createdByUserId +
                ", profile=" + profile +
                ", rateType=" + rateType +
                ", deliveryEstimate=" + deliveryEstimate +
                ", fee=" + fee +
                ", feeDetails=" + feeDetails +
                ", allowedProfileTypes=" + Arrays.toString(allowedProfileTypes) +
                ", guaranteedTargetAmount=" + guaranteedTargetAmount +
                ", ofSourceAmount=" + ofSourceAmount +
                '}';
    }

    private static class FeeDetails {
        BigDecimal transferwise;
        BigDecimal payIn;
        BigDecimal discount;
        BigDecimal partner;

        @Override
        public String toString() {
            return "FeeDetails{" +
                    "transferwise=" + transferwise +
                    ", payIn=" + payIn +
                    ", discount=" + discount +
                    ", partner=" + partner +
                    '}';
        }
    }
}
