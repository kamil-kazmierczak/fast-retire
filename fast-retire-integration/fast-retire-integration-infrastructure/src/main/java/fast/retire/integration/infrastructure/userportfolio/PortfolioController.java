package fast.retire.integration.infrastructure.userportfolio;

import fast.retire.application.portfolioeod.PortfolioEod;
import fast.retire.application.portfolioeod.PortfolioEodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/portfolio")
@Log4j2
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioEodService portfolioEodService;


    @PostMapping("/regenerate/{userId}/{currency}")
    public void regeneratePortfolio(@PathVariable String userId, @PathVariable String currency) {
        portfolioEodService.regeneratePortfolio(userId, currency);
    }

    @GetMapping("/current/{userId}/{currency}")
    public PortfolioResponse getCurrentPortfolio(@PathVariable String userId, @PathVariable String currency) {
        List<PortfolioEod> currentPortfolios = portfolioEodService
                .getPortfolioEodByUserIdAndDate(userId, currency, LocalDate.now().minusDays(10));
        log.debug(currentPortfolios);

        List<PortfolioItemResponse> items = currentPortfolios.stream()
                .map(portfolio -> PortfolioItemResponse.builder()
                        .assetName(portfolio.getAsset())
                        .amount(portfolio.getAmount())
                        .value(portfolio.getComputedValue())
                        .currency(portfolio.getCurrency())
                        .build()
                )
                .toList();

        return new PortfolioResponse(items);
    }


}
