package fast.retire.integration.application.cryptocurrencies;

import fast.retire.integration.api.cryptocurrencies.CryptocurrencyFetcher;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyPriceSaver;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyRequest;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
public class CryptocurrencyScheduler {

    private final CryptocurrencyFetcher cryptocurrencyFetcher;
    private final CryptocurrencyPriceSaver cryptocurrencyPriceSaver;

    @Scheduled(fixedDelayString = "${timer.synchronize-crypto-price.interval}" )
    public void synchronize(CryptocurrencyRequest request) throws Exception {
        log.debug("Timer synchronize-crypto-price started");
        CryptocurrencyResponse response = cryptocurrencyFetcher.fetch(request);
        cryptocurrencyPriceSaver.save(response);
        log.debug("Timer ended");
    }

}