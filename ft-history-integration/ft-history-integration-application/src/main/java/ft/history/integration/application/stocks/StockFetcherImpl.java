package ft.history.integration.application.stocks;

import com.fasterxml.jackson.databind.ObjectMapper;
import ft.history.integration.api.stocks.StockFetcher;
import ft.history.integration.api.stocks.StockRequest;
import ft.history.integration.api.stocks.StockResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Log4j2
public class StockFetcherImpl implements StockFetcher {

    private static final String API_KEY = "0MVECZC8QIEX2KKE";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    public StockResponse fetch(StockRequest request) throws Exception {
        String symbol = request.getSymbol();
        String address = "https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=" + symbol + "&apikey=" + API_KEY;
        var result = restTemplate.getForEntity(address, String.class);
        log.debug("Response from AlphaVantage on symbol {}: {}", symbol, result);

        return objectMapper.readValue(result.getBody(), StockResponse.class);
    }

}