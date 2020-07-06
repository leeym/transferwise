package com.leeym.api.quotes;

import com.leeym.api.profiles.Profile;
import com.leeym.api.profiles.ProfileId;
import com.leeym.api.users.UserId;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

public class Quote {
    private QuoteId id;
    private Currency source;
    private Currency target;
    private BigDecimal sourceAmount;
    private BigDecimal targetAmount;
    private Type type;
    private BigDecimal rate;
    private OffsetDateTime createdTime;
    private UserId createdByUserId;
    private ProfileId profile;
    private RateType rateType;
    private OffsetDateTime deliveryEstimate;
    private BigDecimal fee;
    private FeeDetails feeDetails;
    private Error[] errors;
    private Profile.Type[] allowedProfileTypes;
    private Boolean guaranteedTargetAmount;
    private Boolean ofSourceAmount;

    public QuoteId getId() {
        return id;
    }

    public ProfileId getProfile() {
        return profile;
    }

    public Currency getSource() {
        return source;
    }

    public Currency getTarget() {
        return target;
    }

    public boolean hasErrors() {
        return !getErrors().isEmpty();
    }

    public List<Error> getErrors() {
        return errors == null ? Collections.emptyList() : Arrays.stream(errors).collect(Collectors.toList());
    }

    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public RateType getRateType() {
        return rateType;
    }

    public Type getType() {
        return type;
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

    public static class Error {
        String code;
        String message;
        String targetAmount;

        public String getMessage() {
            return message;
        }
    }
}
