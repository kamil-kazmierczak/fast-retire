package fast.retire.integration.api.stocks;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
public class StockResponse {

    private final String symbol;
    private final Map<LocalDate, BigDecimal> pricePerDate;

}
