package fast.retire.integration.application.cryptocurrencies;

import fast.retire.integration.api.cryptocurrencies.CryptocurrencyFetcher;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyPriceSaver;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyRequest;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class CryptocurrencyScheduler {

    private final CryptocurrencyFetcher cryptocurrencyFetcher;
    private final CryptocurrencyPriceSaver cryptocurrencyPriceSaver;

    @Scheduled(fixedDelayString = "${timer.synchronize-crypto-price.interval}" )
    public void synchronize() throws Exception {
        log.debug("Timer synchronize-crypto-price started");

        List<String> cryptos = List.of("BTC", "ETH", "DOT");

        for (var crypto : cryptos) {
            CryptocurrencyResponse response = cryptocurrencyFetcher.fetch(new CryptocurrencyRequest(crypto, "USD"));
            cryptocurrencyPriceSaver.save(response);
        }

        log.debug("Timer ended");
    }

}