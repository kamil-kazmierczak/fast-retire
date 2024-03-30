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


    public StockResponse fetch() throws Exception {
        var result = restTemplate.getForEntity("https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=IBM&apikey=demo", String.class);
        var s = objectMapper.readValue(result.getBody(), StockResponse.class);
        return s;
    }

}
