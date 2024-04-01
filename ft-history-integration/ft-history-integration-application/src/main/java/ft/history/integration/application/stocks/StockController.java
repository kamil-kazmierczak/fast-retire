package ft.history.integration.application.stocks;

import ft.history.integration.api.stocks.StockFetcher;
import ft.history.integration.api.stocks.StockPriceSaver;
import ft.history.integration.api.stocks.StockRequest;
import ft.history.integration.api.stocks.StockResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integration/stocks")
@Log4j2
@RequiredArgsConstructor
public class StockController {

    private final StockFetcher stockFetcher;
    private final StockPriceSaver stockPriceSaver;


    @PostMapping
    public void synchronize(@RequestBody StockRequest request) throws Exception {
        StockResponse response = stockFetcher.fetch(request);
        stockPriceSaver.save(response);
    }

}