package com.leeym.api.accounts;

import com.leeym.common.Amount;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;

import static java.math.BigDecimal.ZERO;

public class ConversionResponse {
    private ConversionId id;
    private String type;
    private String state;
    private Amount[] balancesAfter;
    private OffsetDateTime creationTime;
    private Step[] steps;
    private Amount sourceAmount;
    private Amount targetAmount;
    private BigDecimal rate;
    private Amount[] feeAmounts;

    public Amount getSourceAmount() {
        return sourceAmount;
    }

    public Amount getTargetAmount() {
        return targetAmount;
    }

    public Amount getFeeAmount() {
        if (feeAmounts == null || feeAmounts.length == 0) {
            return new Amount(sourceAmount.getCurrency(), ZERO);
        } else {
            return Arrays.stream(feeAmounts).reduce(Amount::add).get();
        }
    }

    @Override
    public String toString() {
        return "ConversionResponse{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", state='" + state + '\'' +
                ", balancesAfter=" + Arrays.toString(balancesAfter) +
                ", creationTime=" + creationTime +
                ", steps=" + Arrays.toString(steps) +
                ", sourceAmount=" + sourceAmount +
                ", targetAmount=" + targetAmount +
                ", rate=" + rate +
                ", feeAmounts=" + Arrays.toString(feeAmounts) +
                '}';
    }

    static class Step {
        StepId id;
        String type;
        OffsetDateTime creationTime;
        Amount[] balancesAfter;
        String channelName;
        String channelReferenceId;
        String tracingReferenceCode;
        Amount sourceAmount;
        Amount targetAmount;
        Amount fee;
        BigDecimal rate;

        @Override
        public String toString() {
            return "Step{" +
                    "id=" + id +
                    ", type='" + type + '\'' +
                    ", creationTime=" + creationTime +
                    ", balancesAfter=" + Arrays.toString(balancesAfter) +
                    ", channelName='" + channelName + '\'' +
                    ", channelReferenceId='" + channelReferenceId + '\'' +
                    ", tracingReferenceCode='" + tracingReferenceCode + '\'' +
                    ", sourceAmount=" + sourceAmount +
                    ", targetAmount=" + targetAmount +
                    ", fee=" + fee +
                    ", rate=" + rate +
                    '}';
        }
    }
}
