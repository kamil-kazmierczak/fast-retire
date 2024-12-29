package fast.retire.integration.api.cryptocurrencies;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Builder
@Data
public class CryptocurrencyResponse {

    private final String symbol;
    private final String currency;
    private final Map<LocalDate, BigDecimal> pricePerDate;

}
