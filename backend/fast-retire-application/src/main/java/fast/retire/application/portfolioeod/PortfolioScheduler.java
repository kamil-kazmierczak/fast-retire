package fast.retire.application.portfolioeod;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;

@Log4j2
@RequiredArgsConstructor
public class PortfolioScheduler {

    private final PortfolioEodService portfolioEodService;


    @Scheduled(cron = "${timer.regenerate-portfolio.cron}")
    public void regenerate() {
        log.debug("Timer regenerate-portfolio started");

        portfolioEodService.clearPortfolio("1");
        portfolioEodService.regeneratePortfolio("1", "PLN");

        log.debug("Timer ended");
    }

}