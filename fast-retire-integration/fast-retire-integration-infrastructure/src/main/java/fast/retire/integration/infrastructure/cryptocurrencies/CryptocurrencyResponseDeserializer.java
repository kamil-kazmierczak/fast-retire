package fast.retire.integration.infrastructure.cryptocurrencies;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CryptocurrencyResponseDeserializer extends StdDeserializer<CryptocurrencyResponse> {

    public CryptocurrencyResponseDeserializer() {
        this(null);
    }

    public CryptocurrencyResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CryptocurrencyResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String symbol = node.get("Meta Data").get("2. Digital Currency Code").asText();
        String currency = node.get("Meta Data").get("4. Market Code").asText();

        Map<LocalDate, BigDecimal> pricePerDate = new HashMap<>();

        for (var entry : node.get("Time Series (Digital Currency Daily)").properties()) {
            pricePerDate.put(LocalDate.parse(entry.getKey()), new BigDecimal(entry.getValue().get("4. close").asText()));
        }

        return CryptocurrencyResponse.builder()
                .symbol(symbol)
                .currency(currency)
                .pricePerDate(pricePerDate)
                .build();
    }
}
