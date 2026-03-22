package fast.retire.infrastructure;

import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyResponse;
import fast.retire.integration.api.fxrates.CurrencyRateResponse;
import fast.retire.integration.api.stocks.StockResponse;
import fast.retire.integration.infrastructure.cryptocurrencies.CryptocurrencyResponseDeserializer;
import fast.retire.integration.infrastructure.currencyrates.CurrencyRateResponseDeserializer;
import fast.retire.integration.infrastructure.stocks.StockResponseDeserializer;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class InfrastructureConfiguration {

    @Bean
    public JsonMapper jsonMapper() {
        JsonMapper jsonMapper = new JsonMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(StockResponse.class, new StockResponseDeserializer());
        module.addDeserializer(CryptocurrencyResponse.class, new CryptocurrencyResponseDeserializer());
        module.addDeserializer(CurrencyRateResponse.class, new CurrencyRateResponseDeserializer());

        return jsonMapper;
    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
