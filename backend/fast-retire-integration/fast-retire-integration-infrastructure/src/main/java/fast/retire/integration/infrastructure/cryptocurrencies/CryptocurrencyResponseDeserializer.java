package fast.retire.integration.infrastructure.cryptocurrencies;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CryptocurrencyResponseDeserializer extends StdDeserializer<CryptocurrencyResponse> {

    public CryptocurrencyResponseDeserializer() {
        this(CryptocurrencyResponse.class);
    }

    public CryptocurrencyResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CryptocurrencyResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        JsonNode node = deserializationContext.readTree(jsonParser);
        String symbol = node.get("Meta Data").get("2. Digital Currency Code").asString();
        String currency = node.get("Meta Data").get("4. Market Code").asString();

        Map<LocalDate, BigDecimal> pricePerDate = new HashMap<>();

        for (var entry : node.get("Time Series (Digital Currency Daily)").properties()) {
            pricePerDate.put(LocalDate.parse(entry.getKey()), new BigDecimal(entry.getValue().get("4. close").asString()));
        }

        return CryptocurrencyResponse.builder()
                .symbol(symbol)
                .currency(currency)
                .pricePerDate(pricePerDate)
                .build();
    }
}
