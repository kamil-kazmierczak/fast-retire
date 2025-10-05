package fast.retire.integration.application.currencyrates;

import fast.retire.integration.api.fxrates.CurrencyRateFetcher;
import fast.retire.integration.api.fxrates.CurrencyRateRequest;
import fast.retire.integration.api.fxrates.CurrencyRateResponse;
import fast.retire.integration.api.fxrates.CurrencyRateSaver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integration/currency-rates")
@Log4j2
@RequiredArgsConstructor
public class CurrencyRateController {

    private final CurrencyRateFetcher currencyRateFetcher;
    private final CurrencyRateSaver currencyRateSaver;


    @PostMapping
    public void synchronize(@RequestBody CurrencyRateRequest request) throws Exception {
        CurrencyRateResponse response = currencyRateFetcher.fetch(request);
        currencyRateSaver.save(response);
    }

}