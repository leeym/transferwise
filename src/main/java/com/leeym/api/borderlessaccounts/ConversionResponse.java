package com.leeym.api.borderlessaccounts;

import com.leeym.common.Amount;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

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
    }
}
