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
    BigDecimal value;
    String currency;
    LocalDate date;

}
