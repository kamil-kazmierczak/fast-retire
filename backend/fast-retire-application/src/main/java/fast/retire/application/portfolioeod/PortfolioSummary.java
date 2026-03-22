package fast.retire.application.portfolioeod;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class PortfolioSummary {
    private LocalDate date;
    private String assetType;
    private BigDecimal computedValue;
    private String currency;
    private String userId;

}
