package fast.retire.integration.application.stocks;

import com.fasterxml.jackson.databind.ObjectMapper;
import fast.retire.integration.api.stocks.StockFetcher;
import fast.retire.integration.api.stocks.StockRequest;
import fast.retire.integration.api.stocks.StockResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Log4j2
public class StockFetcherImpl implements StockFetcher {

    private static final String API_KEY = "0MVECZC8QIEX2KKE";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    @Override
    public StockResponse fetch(StockRequest request) throws Exception {
        String symbol = request.getSymbol();
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol + "&outputsize=full&apikey=" + API_KEY;
        var result = restTemplate.getForEntity(url, String.class);
        log.debug("Response from AlphaVantage on symbol {}: {}", symbol, result);

        return objectMapper.readValue(result.getBody(), StockResponse.class);
    }

}