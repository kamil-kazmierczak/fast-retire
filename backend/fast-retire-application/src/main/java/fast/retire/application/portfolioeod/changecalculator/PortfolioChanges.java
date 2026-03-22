package fast.retire.application.portfolioeod.changecalculator;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class PortfolioChanges {
    private BigDecimal dayBeforePercentageChange;
    private BigDecimal dayBeforeValueChange;

    private BigDecimal weekBeforePercentageChange;
    private BigDecimal weekBeforeValueChange;

    private BigDecimal monthBeforePercentageChange;
    private BigDecimal monthBeforeValueChange;
}
