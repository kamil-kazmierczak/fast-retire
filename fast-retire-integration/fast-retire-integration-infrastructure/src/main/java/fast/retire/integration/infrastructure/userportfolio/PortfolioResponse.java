package fast.retire.integration.infrastructure.userportfolio;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class PortfolioResponse {

    private BigDecimal portfolioBalance;
    private BigDecimal dayBeforePortfolioBalance;
    private BigDecimal weekBeforePortfolioBalance;
    private BigDecimal monthBeforePortfolioBalance;

    private String portfolioCurrency;

    List<PortfolioItemResponse> portfolioItems = new ArrayList<>();

}
