package ft.history.integration.infrastructure.stocks;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ft.history.integration.api.stocks.StockResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class StockResponseDeserializer extends StdDeserializer<StockResponse> {

    public StockResponseDeserializer() {
        this(null);
    }

    public StockResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public StockResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String ticker = node.get("Meta Data").get("2. Symbol").asText();

        Map<LocalDate, BigDecimal> pricePerMonth = new HashMap<>();

        for (var entry : node.get("Monthly Time Series").properties()) {
            pricePerMonth.put(LocalDate.parse(entry.getKey()), new BigDecimal(entry.getValue().get("4. close").asText()));
        }

        return StockResponse.builder()
                .ticker(ticker)
                .pricePerMonth(pricePerMonth)
                .build();
    }
}
