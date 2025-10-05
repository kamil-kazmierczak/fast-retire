package fast.retire.integration.api.fxrates;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Builder
@Data
public class CurrencyRateResponse {

    private final String baseCurrency;
    private final String targetCurrency;
    private final Map<LocalDate, BigDecimal> pricePerDate;

}
