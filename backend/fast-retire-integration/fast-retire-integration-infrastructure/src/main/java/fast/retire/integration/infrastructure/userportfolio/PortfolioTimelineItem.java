package fast.retire.integration.infrastructure.userportfolio;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
public class PortfolioTimelineItem {

    String assetName;
    String assetType;
    BigDecimal currentValue;
    BigDecimal valueChangeDaily;
    BigDecimal valueChangeWeekly;
    BigDecimal valueChangeMonthly;
    String currency;
    LocalDate date;

}
