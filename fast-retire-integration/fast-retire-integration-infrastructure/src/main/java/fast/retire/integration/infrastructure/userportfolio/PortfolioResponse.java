package fast.retire.integration.infrastructure.userportfolio;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class PortfolioResponse {

    List<PortfolioItemResponse> portfolioItems = new ArrayList<>();

}
