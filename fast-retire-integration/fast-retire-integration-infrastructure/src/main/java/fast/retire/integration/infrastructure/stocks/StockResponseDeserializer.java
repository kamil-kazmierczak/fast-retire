package fast.retire.integration.infrastructure.stocks;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;
import fast.retire.integration.api.stocks.StockResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class StockResponseDeserializer extends StdDeserializer<StockResponse> {

    public StockResponseDeserializer() {
        this(StockResponse.class);
    }

    public StockResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public StockResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        JsonNode node = deserializationContext.readTree(jsonParser);
        String symbol = node.get("Meta Data").get("2. Symbol").asString();

        Map<LocalDate, BigDecimal> pricePerMonth = new HashMap<>();

        for (var entry : node.get("Time Series (Daily)").properties()) {
            pricePerMonth.put(LocalDate.parse(entry.getKey()), new BigDecimal(entry.getValue().get("4. close").asString()));
        }

        return StockResponse.builder()
                .symbol(symbol)
                .pricePerDate(pricePerMonth)
                .build();
    }
}
