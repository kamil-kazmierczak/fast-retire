package fast.retire.integration.api.cryptocurrencies;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
public class CryptocurrencyResponse {

    private final String symbol;
    private final String currency;
    private final Map<LocalDate, BigDecimal> pricePerDate;

}
