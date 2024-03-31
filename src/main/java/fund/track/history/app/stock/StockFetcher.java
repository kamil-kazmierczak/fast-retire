package fund.track.history.app.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Log4j2
public class StockFetcher {

    private static final String API_KEY = "0MVECZC8QIEX2KKE";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    public StockResponse fetch(String symbol) throws Exception {
        String address = "https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=" + symbol + "&apikey=" + API_KEY;
        var result = restTemplate.getForEntity(address, String.class);
        log.debug("Response from AlphaVantage on symbol {}: {}", symbol, result);

        return objectMapper.readValue(result.getBody(), StockResponse.class);
    }

}
