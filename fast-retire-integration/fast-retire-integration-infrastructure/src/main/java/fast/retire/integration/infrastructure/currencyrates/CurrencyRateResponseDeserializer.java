package fast.retire.integration.infrastructure.currencyrates;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import fast.retire.integration.api.fxrates.CurrencyRateResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class CurrencyRateResponseDeserializer extends StdDeserializer<CurrencyRateResponse> {

    public CurrencyRateResponseDeserializer() {
        this(null);
    }

    public CurrencyRateResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CurrencyRateResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String baseCurrency = node.get("base").asText();
        String targetCurrency = node.get("rates").properties().iterator().next().getValue().properties()
                .iterator().next().getKey();

        Map<LocalDate, BigDecimal> pricePerDate = new HashMap<>();

        for (var entry : node.get("rates").properties()) {
            if (entry.getValue().properties().iterator().hasNext()) {
                pricePerDate.put(Instant.parse(entry.getKey()).atZone(ZoneId.systemDefault()).toLocalDate(),
                        new BigDecimal(entry.getValue().properties().iterator().next().getValue().asText()));
            }
        }

        return CurrencyRateResponse.builder()
                .baseCurrency(baseCurrency)
                .targetCurrency(targetCurrency)
                .pricePerDate(pricePerDate)
                .build();
    }
}
