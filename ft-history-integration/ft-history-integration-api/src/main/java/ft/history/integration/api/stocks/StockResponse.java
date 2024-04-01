package ft.history.integration.api.stocks;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Builder
@Data
public class StockResponse {

    private final String ticker;
    private final Map<LocalDate, BigDecimal> pricePerMonth;

}
