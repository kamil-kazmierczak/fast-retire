package fast.retire.integration.infrastructure.userportfolio;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PortfolioResponse {

    String id;
    String asset;

}
