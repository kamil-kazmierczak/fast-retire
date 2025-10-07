package fast.retire.application.portfolioeod.changecalculator;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class PortfolioChanges {
    private BigDecimal dayBeforeValueChange;
    private BigDecimal weekBeforeValueChange;
    private BigDecimal monthBeforeValueChange;
}
