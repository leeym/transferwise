package com.leeym.api.borderlessaccounts;

import com.leeym.common.Amount;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;

public class ConversionResponse {
    ConversionId id;
    String type;
    String state;
    Amount[] balancesAfter;
    OffsetDateTime creationTime;
    Step[] steps;
    Amount sourceAmount;
    Amount targetAmount;
    BigDecimal rate;
    Amount[] feeAmounts;

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
