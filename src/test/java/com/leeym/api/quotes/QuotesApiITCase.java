package com.leeym.api.quotes;

import com.leeym.api.BaseApiITCase;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static com.leeym.api.Stage.SANDBOX;
import static com.leeym.api.quotes.Type.BALANCE_CONVERSION;
import static org.junit.jupiter.api.Assertions.assertEquals;

class QuotesApiITCase extends BaseApiITCase {

    private final QuotesApi api = new QuotesApi(SANDBOX, REAL_SANDBOX_API_TOKEN);

    @Test
    public void testCreateQuote() {
        QuoteRequest request = new QuoteRequest(REAL_SANDBOX_PERSONAL_PROFILE_ID, Currency.getInstance("EUR"),
                        Currency.getInstance("GBP"), new BigDecimal(600), BALANCE_CONVERSION);
        QuoteResponse response = api.createQuote(request);
        assertEquals(REAL_SANDBOX_PERSONAL_PROFILE_ID, response.profile);
        assertEquals("EUR", response.source.getCurrencyCode());
        assertEquals("GBP", response.target.getCurrencyCode());
        assertEquals(600, response.targetAmount.intValue());
        assertEquals(RateType.FIXED, response.rateType);
        assertEquals(BALANCE_CONVERSION, response.type);
    }
}
