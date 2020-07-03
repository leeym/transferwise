package com.leeym.api.quotes;

import com.leeym.api.BaseApiITCase;
import com.leeym.api.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

class QuotesApiITCase extends BaseApiITCase {

    private final QuotesApi api = new QuotesApi(Stage.SANDBOX, REAL_SANDBOX_API_TOKEN);

    @Test
    public void testCreateQuote() {
        QuoteRequest request =
                new QuoteRequest(REAL_SANDBOX_PERSONAL_PROFILE_ID, Currency.getInstance("EUR"),
                        Currency.getInstance("GBP"), new BigDecimal(600), Type.BALANCE_CONVERSION);
        QuoteResponse response = api.createQuote(request);
        Assertions.assertEquals(REAL_SANDBOX_PERSONAL_PROFILE_ID, response.profile);
        Assertions.assertEquals("EUR", response.source.getCurrencyCode());
        Assertions.assertEquals("GBP", response.target.getCurrencyCode());
        Assertions.assertEquals(600, response.targetAmount.intValue());
        Assertions.assertEquals(RateType.FIXED, response.rateType);
        Assertions.assertEquals(Type.BALANCE_CONVERSION, response.type);
    }
}
