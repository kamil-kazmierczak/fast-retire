package fast.retire.integration.infrastructure.userportfolio;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class PortfolioItemResponse {

    String assetName;
    String assetType;
    BigDecimal amount;
    BigDecimal currentValue;

    BigDecimal dailyPercentageChange;
    BigDecimal weeklyPercentageChange;
    BigDecimal monthlyPercentageChange;

    BigDecimal dailyValueChange;
    BigDecimal weeklyValueChange;
    BigDecimal monthlyValueChange;

    String currency;

}
