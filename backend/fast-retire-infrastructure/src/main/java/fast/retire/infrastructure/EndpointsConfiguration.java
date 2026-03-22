package fast.retire.infrastructure;

import fast.retire.application.portfolioeod.PortfolioEodService;
import fast.retire.application.portfolioeod.changecalculator.PortfolioChangeCalculator;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyFetcher;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyPriceSaver;
import fast.retire.integration.api.fxrates.CurrencyRateFetcher;
import fast.retire.integration.api.fxrates.CurrencyRateSaver;
import fast.retire.integration.api.stocks.StockFetcher;
import fast.retire.integration.api.stocks.StockPriceSaver;
import fast.retire.integration.application.cryptocurrencies.CryptocurrencyController;
import fast.retire.integration.application.currencyrates.CurrencyRateController;
import fast.retire.integration.application.stocks.StockController;
import fast.retire.integration.infrastructure.userportfolio.PortfolioController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointsConfiguration {

    @Bean
    public StockController stockController(
            StockFetcher stockFetcher,
            StockPriceSaver stockPriceSaver) {
        return new StockController(stockFetcher, stockPriceSaver);
    }

    @Bean
    public CryptocurrencyController cryptocurrencyController(
            CryptocurrencyFetcher cryptocurrencyFetcher,
            CryptocurrencyPriceSaver cryptocurrencyPriceSaver) {
        return new CryptocurrencyController(cryptocurrencyFetcher, cryptocurrencyPriceSaver);
    }

    @Bean
    public CurrencyRateController currencyRateController(
            CurrencyRateFetcher currencyRateFetcher,
            CurrencyRateSaver currencyRateSaver) {
        return new CurrencyRateController(currencyRateFetcher, currencyRateSaver);
    }

    @Bean
    public PortfolioController userPortfolioController(
            PortfolioEodService portfolioEodService,
            PortfolioChangeCalculator portfolioChangeCalculator) {
        return new PortfolioController(portfolioEodService, portfolioChangeCalculator);
    }

}
