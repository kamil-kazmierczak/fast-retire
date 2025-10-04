package fast.retire.infrastructure;

import fast.retire.application.userportfolio.UserPortfolioService;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyFetcher;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyPriceSaver;
import fast.retire.integration.api.fxrates.FxRateFetcher;
import fast.retire.integration.api.fxrates.FxRateSaver;
import fast.retire.integration.api.stocks.StockFetcher;
import fast.retire.integration.api.stocks.StockPriceSaver;
import fast.retire.integration.application.cryptocurrencies.CryptocurrencyController;
import fast.retire.integration.application.fxrates.FxRateController;
import fast.retire.integration.application.stocks.StockController;
import fast.retire.integration.infrastructure.userportfolio.UserPortfolioController;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
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
    public FxRateController fxRateController(
            FxRateFetcher fxRateFetcher,
            FxRateSaver fxRateSaver) {
        return new FxRateController(fxRateFetcher, fxRateSaver);
    }

    @Bean
    public UserPortfolioController userPortfolioController(
            UserPortfolioService userPortfolioService) {
        return new UserPortfolioController(userPortfolioService);
    }

}
