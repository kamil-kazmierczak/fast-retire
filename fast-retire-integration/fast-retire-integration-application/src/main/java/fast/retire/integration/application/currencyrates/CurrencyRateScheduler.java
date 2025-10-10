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


    @Scheduled(cron = "${timer.synchronize-currency-rate.cron}" )
    public void synchronize() throws Exception {
        log.debug("Timer synchronize-currency-rate started");

        CurrencyRateResponse response = currencyRateFetcher.fetch(new CurrencyRateRequest("USD", "PLN"));
        currencyRateSaver.save(response);

        log.debug("Timer ended");
    }

}