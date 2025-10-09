package fast.retire.integration.application.currencyrates;

import fast.retire.integration.api.fxrates.CurrencyRateFetcher;
import fast.retire.integration.api.fxrates.CurrencyRateRequest;
import fast.retire.integration.api.fxrates.CurrencyRateResponse;
import fast.retire.integration.api.fxrates.CurrencyRateSaver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;

@Log4j2
@RequiredArgsConstructor
public class CurrencyRateScheduler {

    private final CurrencyRateFetcher currencyRateFetcher;
    private final CurrencyRateSaver currencyRateSaver;


    @Scheduled(fixedDelayString = "${timer.synchronize-currency-rate.interval}" )
    public void synchronize(CurrencyRateRequest request) throws Exception {
        log.debug("Timer synchronize-currency-rate started");

        CurrencyRateResponse response = currencyRateFetcher.fetch(request);
        currencyRateSaver.save(response);

        log.debug("Timer ended");
    }

}