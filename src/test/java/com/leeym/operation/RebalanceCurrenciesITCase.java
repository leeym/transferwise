package com.leeym.operation;

import com.leeym.api.BaseApiITCase;
import com.leeym.api.borderlessaccounts.BorderlessAccountsApi;
import com.leeym.api.exchangerates.RatesApi;
import com.leeym.api.quotes.QuotesApi;
import org.junit.jupiter.api.Test;

import static com.leeym.common.BaseUrl.SANDBOX_BASEURL;


class RebalanceCurrenciesITCase extends BaseApiITCase {

    BorderlessAccountsApi borderlessAccountsApi = new BorderlessAccountsApi(SANDBOX_BASEURL, SANDBOX_API_TOKEN);
    RatesApi ratesApi = new RatesApi(SANDBOX_BASEURL, SANDBOX_API_TOKEN);
    QuotesApi quotesApi = new QuotesApi(SANDBOX_BASEURL, SANDBOX_API_TOKEN);

    @Test
    public void testCall() {
        RebalanceCurrencies rebalanceCurrencies =
                new RebalanceCurrencies(SANDBOX_PERSONAL_PROFILE_ID, borderlessAccountsApi, ratesApi, quotesApi);
        rebalanceCurrencies.call();
    }
}
