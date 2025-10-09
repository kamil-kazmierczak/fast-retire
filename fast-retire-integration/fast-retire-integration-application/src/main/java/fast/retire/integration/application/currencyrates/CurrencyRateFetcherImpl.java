package fast.retire.integration.application.currencyrates;

import com.fasterxml.jackson.databind.ObjectMapper;
import fast.retire.integration.api.fxrates.CurrencyRateFetcher;
import fast.retire.integration.api.fxrates.CurrencyRateRequest;
import fast.retire.integration.api.fxrates.CurrencyRateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@RequiredArgsConstructor
@Log4j2
public class CurrencyRateFetcherImpl implements CurrencyRateFetcher {

    private static final String API_KEY = "fxr_live_d374de9d27c3038b38513faad079dcfe6026";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public CurrencyRateResponse fetch(CurrencyRateRequest request) throws Exception {
        String startDate = LocalDate.now().minusMonths(1).toString();
        String endDate = LocalDate.now().minusDays(1).toString();

        String url = "https://api.fxratesapi.com/timeseries?api_key=" + API_KEY + "&places=2&currencies=" + request.getTargetCurrency()
                + "&start_date=" + startDate + "&end_date=" + endDate;
        var result = restTemplate.getForEntity(url, String.class);
        log.debug("Response from FxRatesApi on {}-{}: {}",
                request.getBaseCurrency(),
                request.getTargetCurrency(),
                result);

        return objectMapper.readValue(result.getBody(), CurrencyRateResponse.class);
    }

}