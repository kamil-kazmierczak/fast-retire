package fast.retire.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import fast.retire.api.HistoryRepository;
import fast.retire.api.register.HistoryRegisterRepository;
import fast.retire.integration.api.stocks.StockFetcher;
import fast.retire.integration.api.stocks.StockPriceSaver;
import fast.retire.integration.application.stocks.StockFetcherImpl;
import fast.retire.integration.application.stocks.StockPriceSaverImpl;
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
