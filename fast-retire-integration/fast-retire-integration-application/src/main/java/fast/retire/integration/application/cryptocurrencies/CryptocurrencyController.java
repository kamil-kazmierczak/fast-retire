package fast.retire.integration.application.cryptocurrencies;

import fast.retire.integration.api.cryptocurrencies.CryptocurrencyFetcher;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyPriceSaver;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyRequest;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integration/cryptocurrencies")
@Log4j2
@RequiredArgsConstructor
public class CryptocurrencyController {

    private final CryptocurrencyFetcher cryptocurrencyFetcher;
    private final CryptocurrencyPriceSaver cryptocurrencyPriceSaver;


    @PostMapping
    public void synchronize(@RequestBody CryptocurrencyRequest request) throws Exception {
        CryptocurrencyResponse response = cryptocurrencyFetcher.fetch(request);
        cryptocurrencyPriceSaver.save(response);
    }

}