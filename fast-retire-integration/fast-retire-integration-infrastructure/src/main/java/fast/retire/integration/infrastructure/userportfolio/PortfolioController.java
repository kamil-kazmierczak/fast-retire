package fast.retire.integration.infrastructure.userportfolio;

import fast.retire.application.portfolioeod.PortfolioEodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
