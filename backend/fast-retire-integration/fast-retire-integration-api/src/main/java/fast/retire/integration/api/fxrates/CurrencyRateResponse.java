package fast.retire.integration.api.fxrates;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
public class CurrencyRateResponse {

    private final String baseCurrency;
    private final String targetCurrency;
    private final Map<LocalDate, BigDecimal> pricePerDate;

}
