package ft.history.infrastructure;

import ft.history.api.HistoryRepository;
import ft.history.application.history.HistoryController;
import ft.history.integration.api.stocks.StockFetcher;
import ft.history.integration.api.stocks.StockPriceSaver;
import ft.history.integration.application.stocks.StockController;
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
