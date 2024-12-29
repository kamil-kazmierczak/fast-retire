package fast.retire.infrastructure;

import fast.retire.api.HistoryRepository;
import fast.retire.application.history.HistoryController;
import fast.retire.integration.api.stocks.StockFetcher;
import fast.retire.integration.api.stocks.StockPriceSaver;
import fast.retire.integration.application.stocks.StockController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointsConfiguration {

    @Bean
    public HistoryController historyController(
            HistoryRepository historyRepository) {
        return new HistoryController(historyRepository);
    }

    @Bean
    public StockController stockController(
            StockFetcher stockFetcher,
            StockPriceSaver stockPriceSaver) {
        return new StockController(stockFetcher, stockPriceSaver);
    }

}
