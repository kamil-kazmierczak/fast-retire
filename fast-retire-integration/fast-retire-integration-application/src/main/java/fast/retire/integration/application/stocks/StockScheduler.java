package fast.retire.integration.application.stocks;

import fast.retire.integration.api.stocks.StockFetcher;
import fast.retire.integration.api.stocks.StockPriceSaver;
import fast.retire.integration.api.stocks.StockRequest;
import fast.retire.integration.api.stocks.StockResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;

@Log4j2
@RequiredArgsConstructor
public class StockScheduler {

    private final StockFetcher stockFetcher;
    private final StockPriceSaver stockPriceSaver;


    @Scheduled(fixedDelayString = "${timer.synchronize-stock-price.interval}" )
    public void synchronize(StockRequest request) throws Exception {
        log.debug("Timer synchronize-stock-price started");

        StockResponse response = stockFetcher.fetch(request);
        stockPriceSaver.save(response);

        log.debug("Timer ended");
    }

}