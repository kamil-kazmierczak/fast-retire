package fast.retire.integration.application.cryptocurrencies;

import tools.jackson.databind.ObjectMapper;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyFetcher;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyRequest;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Log4j2
public class CryptocurrencyFetcherImpl implements CryptocurrencyFetcher {

    private static final String API_KEY = "0MVECZC8QIEX2KKE";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    @Override
    public CryptocurrencyResponse fetch(CryptocurrencyRequest request) throws Exception {
        String symbol = request.getSymbol();
        String currency = request.getCurrency();

        String url = "https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_DAILY&symbol=" + symbol
                + "&market=" + currency + "&apikey=" + API_KEY;
        var result = restTemplate.getForEntity(url, String.class);
        log.debug("Response from AlphaVantage on symbol {}, currency {}: {}", symbol, currency, result);

        return objectMapper.readValue(result.getBody(), CryptocurrencyResponse.class);
    }

}