package fast.retire.integration.infrastructure.userportfolio;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Builder
@Data
public class UserPortfolioResponse {

    String id;
    String asset;

}
