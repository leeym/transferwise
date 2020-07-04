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
    public void testCreateQuote() {
        BigDecimal amount = new BigDecimal("600");
        QuoteRequest request = new QuoteRequest(SANDBOX_PERSONAL_PROFILE_ID, EUR, GBP, amount, BALANCE_CONVERSION);
        QuoteResponse response = api.createQuote(request);
        assertEquals(SANDBOX_PERSONAL_PROFILE_ID, response.profile);
        assertEquals(EUR, response.source);
        assertEquals(GBP, response.target);
        assertEquals(amount.longValue(), response.targetAmount.longValue());
        assertEquals(RateType.FIXED, response.rateType);
        assertEquals(BALANCE_CONVERSION, response.type);
    }
}
