package fast.retire.infrastructure;

import tools.jackson.databind.ObjectMapper;
import fast.retire.application.currencyrates.CurrencyRateRepository;
import fast.retire.application.history.HistoryRepository;
import fast.retire.application.portfolioeod.PortfolioEodRepository;
import fast.retire.application.portfolioeod.PortfolioEodService;
import fast.retire.application.portfolioeod.PortfolioScheduler;
import fast.retire.application.portfolioeod.changecalculator.PortfolioChangeCalculator;
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
import fast.retire.integration.application.cryptocurrencies.CryptocurrencyScheduler;
import fast.retire.integration.application.currencyrates.CurrencyRateFetcherImpl;
import fast.retire.integration.application.currencyrates.CurrencyRateSaverImpl;
import fast.retire.integration.application.currencyrates.CurrencyRateScheduler;
import fast.retire.integration.application.stocks.StockFetcherImpl;
import fast.retire.integration.application.stocks.StockPriceSaverImpl;
import fast.retire.integration.application.stocks.StockScheduler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Configuration
@Import({
        JpaConfiguration.class,
        InfrastructureConfiguration.class,
        EndpointsConfiguration.class,
        FiltersConfiguration.class
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

    @Bean
    public PortfolioChangeCalculator portfolioChangeCalculator() {
        return new PortfolioChangeCalculator();
    }

    @Bean
    @ConditionalOnProperty(
            name = "timer.enabled",
            havingValue = "true"
    )
    public CryptocurrencyScheduler cryptocurrencyScheduler(
            CryptocurrencyFetcher cryptocurrencyFetcher,
            CryptocurrencyPriceSaver cryptocurrencyPriceSaver
    ) {
        return new CryptocurrencyScheduler(cryptocurrencyFetcher, cryptocurrencyPriceSaver);
    }

    @Bean
    @ConditionalOnProperty(
            name = "timer.enabled",
            havingValue = "true"
    )
    public CurrencyRateScheduler currencyRateScheduler(
            CurrencyRateFetcher currencyRateFetcher,
            CurrencyRateSaver currencyRateSaver
    ) {
        return new CurrencyRateScheduler(currencyRateFetcher, currencyRateSaver);
    }

    @Bean
    @ConditionalOnProperty(
            name = "timer.enabled",
            havingValue = "true"
    )
    public StockScheduler stockScheduler(
            StockFetcher stockFetcher,
            StockPriceSaver stockPriceSaver
    ) {
        return new StockScheduler(stockFetcher, stockPriceSaver);
    }

    @Bean
    @ConditionalOnProperty(
            name =  "timer.enabled",
            havingValue = "true"
    )
    public PortfolioScheduler portfolioScheduler(PortfolioEodService portfolioEodService) {
        return new PortfolioScheduler(portfolioEodService);
    }

}