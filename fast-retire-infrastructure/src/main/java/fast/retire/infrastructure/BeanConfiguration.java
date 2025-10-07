package fast.retire.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import fast.retire.application.currencyrates.CurrencyRateRepository;
import fast.retire.application.history.HistoryRepository;
import fast.retire.application.portfolioeod.PortfolioEodRepository;
import fast.retire.application.portfolioeod.PortfolioEodService;
import fast.retire.application.portfolioeod.generator.PortfolioEodGenerator;
import fast.retire.application.user.UserRepository;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyFetcher;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyPriceSaver;
import fast.retire.integration.api.fxrates.CurrencyRateFetcher;
import fast.retire.integration.api.fxrates.CurrencyRateSaver;
import fast.retire.integration.api.stocks.StockFetcher;
import fast.retire.integration.api.stocks.StockPriceSaver;
import fast.retire.integration.application.cryptocurrencies.CryptocurrencyFetcherImpl;
import fast.retire.integration.application.cryptocurrencies.CryptocurrencyPriceSaverImpl;
import fast.retire.integration.application.currencyrates.CurrencyRateFetcherImpl;
import fast.retire.integration.application.currencyrates.CurrencyRateSaverImpl;
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
            HistoryRepository historyRepository) {
        return new StockPriceSaverImpl(historyRepository);
    }

    @Bean
    public CryptocurrencyFetcher cryptocurrencyFetcher(
            RestTemplate restTemplate,
            ObjectMapper objectMapper) {
        return new CryptocurrencyFetcherImpl(restTemplate, objectMapper);
    }

    @Bean
    public CryptocurrencyPriceSaver cryptocurrencyPriceSaver(
            HistoryRepository historyRepository) {
        return new CryptocurrencyPriceSaverImpl(historyRepository);
    }

    @Bean
    public CurrencyRateFetcher currencyRateFetcher(
            RestTemplate restTemplate,
            ObjectMapper objectMapper) {
        return new CurrencyRateFetcherImpl(restTemplate, objectMapper);
    }

    @Bean
    public CurrencyRateSaver currencyRateSaver(
            CurrencyRateRepository currencyRateRepository) {
        return new CurrencyRateSaverImpl(currencyRateRepository);
    }

    @Bean
    public PortfolioEodService userPortfolioService(
            PortfolioEodRepository portfolioEodRepository,
            PortfolioEodGenerator portfolioEodGenerator) {
        return new PortfolioEodService(portfolioEodRepository, portfolioEodGenerator);
    }

    @Bean
    public PortfolioEodGenerator portfolioEodGenerator(
            PortfolioEodRepository portfolioEodRepository,
            UserRepository userRepository,
            HistoryRepository historyRepository,
            CurrencyRateRepository currencyRateRepository) {
        return new PortfolioEodGenerator(
                portfolioEodRepository,
                userRepository,
                historyRepository,
                currencyRateRepository

        );
    }

}
