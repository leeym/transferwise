package com.leeym.api.quotes;

import com.leeym.api.BaseAPITest;
import com.leeym.api.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

class QuotesAPITest extends BaseAPITest {

    private final QuotesAPI api = new QuotesAPI(Stage.SANDBOX, REAL_SANDBOX_API_TOKEN);

    @Disabled
    @Test
    public void testCreateQuote() {
        QuoteRequest request =
                new QuoteRequest(REAL_SANDBOX_PERSONAL_PROFILE_ID, Currency.getInstance("EUR"),
                        Currency.getInstance("GBP"), new BigDecimal(600), Type.BALANCE_CONVERSION);
        QuoteResponse response = api.createQuote(request);
        Assertions.assertEquals(REAL_SANDBOX_PERSONAL_PROFILE_ID.toString(), response.profile);
        Assertions.assertEquals("EUR", response.source.getCurrencyCode());
        Assertions.assertEquals("GBP", response.target.getCurrencyCode());
        Assertions.assertEquals(600, response.targetAmount.intValue());
        Assertions.assertEquals(RateType.FIXED, response.rateType);
        Assertions.assertEquals(Type.BALANCE_CONVERSION, response.type);
    }
}
