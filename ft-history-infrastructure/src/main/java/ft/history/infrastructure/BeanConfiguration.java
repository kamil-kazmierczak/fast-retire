package ft.history.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import ft.history.api.HistoryRepository;
import ft.history.api.register.HistoryRegisterRepository;
import ft.history.application.history.HistoryController;
import ft.history.integration.api.stocks.StockFetcher;
import ft.history.integration.api.stocks.StockPriceSaver;
import ft.history.integration.application.stocks.StockFetcherImpl;
import ft.history.integration.application.stocks.StockPriceSaverImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Configuration
@Import({
        ElasticsearchConfiguration.class,
        JpaConfiguration.class,
        InfrastructureConfiguration.class,
        EndpointsConfiguration.class
})
public class BeanConfiguration {

    @Bean
    public StockFetcher stockFetcher(
            RestTemplate restTemplate,
            ObjectMapper objectMapper) {
        return new StockFetcherImpl(restTemplate, objectMapper);
    }

    @Bean
    public StockPriceSaver stockPriceSaver(
            HistoryRegisterRepository historyRegisterRepository,
            HistoryRepository historyRepository) {
        return new StockPriceSaverImpl(historyRegisterRepository, historyRepository);
    }

}
