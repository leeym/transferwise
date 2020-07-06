package com.leeym.operation;

import com.leeym.api.BaseApiITCase;
import com.leeym.api.accounts.AccountsApi;
import com.leeym.api.quotes.QuotesApi;
import com.leeym.api.rates.RatesApi;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.leeym.common.BaseUrl.SANDBOX_BASEURL;

class RebalanceCurrenciesITCase extends BaseApiITCase {

    AccountsApi accountsApi = new AccountsApi(SANDBOX_BASEURL, SANDBOX_API_TOKEN);
    RatesApi ratesApi = new RatesApi(SANDBOX_BASEURL, SANDBOX_API_TOKEN);
    QuotesApi quotesApi = new QuotesApi(SANDBOX_BASEURL, SANDBOX_API_TOKEN);

    @Test
    public void testCall() {
        RebalanceCurrencies r = new RebalanceCurrencies(SANDBOX_PERSONAL_PROFILE_ID, accountsApi, ratesApi, quotesApi);
        List<String> logs = r.call();
        System.err.println("=".repeat(120));
        System.err.println(String.join("\n", logs));
    }
}
