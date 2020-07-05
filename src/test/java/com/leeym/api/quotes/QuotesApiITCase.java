package com.leeym.api.quotes;

import com.leeym.api.BaseApiITCase;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.leeym.api.Currencies.EUR;
import static com.leeym.api.Currencies.GBP;
import static com.leeym.api.quotes.Type.BALANCE_CONVERSION;
import static com.leeym.common.BaseUrl.SANDBOX_BASEURL;
import static org.junit.jupiter.api.Assertions.assertEquals;

class QuotesApiITCase extends BaseApiITCase {

    private final QuotesApi api = new QuotesApi(SANDBOX_BASEURL, SANDBOX_API_TOKEN);

    @Test
    public void buyTargetFromSource() {
        BigDecimal amount = new BigDecimal("600");
        Quote response = api.buyTargetFromSource(SANDBOX_PERSONAL_PROFILE_ID, EUR, GBP, amount);
        assertEquals(SANDBOX_PERSONAL_PROFILE_ID, response.getProfile());
        assertEquals(EUR, response.getSource());
        assertEquals(GBP, response.getTarget());
        assertEquals(amount.longValue(), response.getTargetAmount().longValue());
        assertEquals(RateType.FIXED, response.getRateType());
        assertEquals(BALANCE_CONVERSION, response.getType());
    }

    @Test
    public void sellSourceToTarget() {
        BigDecimal amount = new BigDecimal("600");
        Quote response = api.sellSourceToTarget(SANDBOX_PERSONAL_PROFILE_ID, GBP, amount, EUR);
        assertEquals(SANDBOX_PERSONAL_PROFILE_ID, response.getProfile());
        assertEquals(GBP, response.getSource());
        assertEquals(amount.longValue(), response.getSourceAmount().longValue());
        assertEquals(EUR, response.getTarget());
        assertEquals(RateType.FIXED, response.getRateType());
        assertEquals(BALANCE_CONVERSION, response.getType());
    }
}
