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
    BigDecimal dailyChange;
    BigDecimal weeklyChange;
    BigDecimal monthlyChange;
    String currency;

}
