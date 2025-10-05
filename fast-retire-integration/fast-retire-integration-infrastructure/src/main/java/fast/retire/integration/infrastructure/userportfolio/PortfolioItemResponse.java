package fast.retire.integration.infrastructure.userportfolio;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class PortfolioItemResponse {

    String assetName;
    BigDecimal amount;
    BigDecimal value;
    String currency;

}
