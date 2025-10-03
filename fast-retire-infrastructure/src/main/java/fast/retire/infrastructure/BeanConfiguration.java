package fast.retire.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import fast.retire.api.register.HistoryRegisterRepository;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyFetcher;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyPriceSaver;
import fast.retire.integration.api.fxrates.FxRateFetcher;
import fast.retire.integration.api.fxrates.FxRateSaver;
import fast.retire.integration.api.stocks.StockFetcher;
import fast.retire.integration.api.stocks.StockPriceSaver;
import fast.retire.integration.application.cryptocurrencies.CryptocurrencyFetcherImpl;
import fast.retire.integration.application.cryptocurrencies.CryptocurrencyPriceSaverImpl;
import fast.retire.integration.application.fxrates.FxRateFetcherImpl;
import fast.retire.integration.application.fxrates.FxRateSaverImpl;
import fast.retire.integration.application.stocks.StockFetcherImpl;
import fast.retire.integration.application.stocks.StockPriceSaverImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Configuration
@Import({
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
            HistoryRegisterRepository historyRegisterRepository) {
        return new StockPriceSaverImpl(historyRegisterRepository);
    }

    @Bean
    public CryptocurrencyFetcher cryptocurrencyFetcher(
            RestTemplate restTemplate,
            ObjectMapper objectMapper) {
        return new CryptocurrencyFetcherImpl(restTemplate, objectMapper);
    }

    @Bean
    public CryptocurrencyPriceSaver cryptocurrencyPriceSaver(
            HistoryRegisterRepository historyRegisterRepository) {
        return new CryptocurrencyPriceSaverImpl(historyRegisterRepository);
    }

    @Bean
    public FxRateFetcher fxRateFetcher(
            RestTemplate restTemplate,
            ObjectMapper objectMapper) {
        return new FxRateFetcherImpl(restTemplate, objectMapper);
    }

    @Bean
    public FxRateSaver fxRateSaver(
            HistoryRegisterRepository historyRegisterRepository) {
        return new FxRateSaverImpl(historyRegisterRepository);
    }

}
