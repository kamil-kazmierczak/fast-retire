package fast.retire.integration.infrastructure.userportfolio;

import fast.retire.application.portfolioeod.PortfolioEod;
import fast.retire.application.portfolioeod.PortfolioEodService;
import fast.retire.application.portfolioeod.PortfolioSummary;
import fast.retire.application.portfolioeod.changecalculator.PortfolioChangeCalculator;
import fast.retire.application.portfolioeod.changecalculator.PortfolioChanges;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/portfolio")
@Log4j2
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioEodService portfolioEodService;
    private final PortfolioChangeCalculator portfolioChangeCalculator;

    @PostMapping("/clear/{userId}")
    public void clearPortfolio(@PathVariable String userId) {
        portfolioEodService.clearPortfolio(userId);
    }

    @PostMapping("/regenerate/{userId}/{currency}")
    public void regeneratePortfolio(@PathVariable String userId, @PathVariable String currency) {
        portfolioEodService.regeneratePortfolio(userId, currency);
    }

    @GetMapping("/summary/{assetType}/{userId}/{currency}")
    public PortfolioTimelineResponse getPortfolioSummary(
            @PathVariable String userId,
            @PathVariable String currency,
            @PathVariable String assetType) {
        List<PortfolioSummary> portfolioSummary = portfolioEodService.getPortfolioSummary(userId, currency, assetType);

        List<PortfolioTimelineItem> items = portfolioSummary.stream()
                .map(summary -> PortfolioTimelineItem.builder()
                        .assetType(assetType)
                        .assetName(assetType)
                        .date(summary.getDate())
                        .currentValue(summary.getComputedValue())
                        .currency(summary.getCurrency())
                        .build())
                .sorted(Comparator.comparing(PortfolioTimelineItem::getDate))
                .toList();
        return new PortfolioTimelineResponse(items);
    }

    @GetMapping("/timeline/{assetName}/{userId}/{currency}")
    public PortfolioTimelineResponse getPortfolioTimelineResponse(
            @PathVariable String userId,
            @PathVariable String currency,
            @PathVariable String assetName) {
        var portfolioEods = portfolioEodService.getPortfolioEodsByUserIdFromDateTillNow(userId, currency, assetName);
        List<PortfolioTimelineItem> items = portfolioEods.stream()
                .map(portfolioEod -> PortfolioTimelineItem.builder()
                        .assetName(portfolioEod.getAssetName())
                        .assetType(portfolioEod.getAssetType())
                        .currentValue(portfolioEod.getComputedValue())
                        .currency(portfolioEod.getCurrency())
                        .date(portfolioEod.getDate())
                        .build()
                ).toList();
        return new PortfolioTimelineResponse(items);
    }

    @GetMapping("/current/{userId}/{currency}")
    public PortfolioResponse getCurrentPortfolio(@PathVariable String userId, @PathVariable String currency) {
        LocalDate currentDate = LocalDate.now().minusDays(1);
        LocalDate lastDay = currentDate.with(new LastWorkingDayAdjuster());
        LocalDate lastWeek = currentDate.with(new LastWorkingDayWeekBeforeAdjuster());
        LocalDate lastMonth = currentDate.with(new LastWorkingDayMonthBeforeAdjuster());

        log.debug("Some message");

        List<PortfolioEod> currentPortfolios = portfolioEodService
                .getPortfolioEodByUserIdAndDate(userId, currency, currentDate);
        var dayBeforePortfolios = portfolioEodService
                .getPortfolioEodByUserIdAndDate(userId, currency, lastDay);
        var weekBeforePortfolios = portfolioEodService
                .getPortfolioEodByUserIdAndDate(userId, currency, lastWeek);
        var monthBeforePortfolios = portfolioEodService
                .getPortfolioEodByUserIdAndDate(userId, currency, lastMonth);

        Map<String, PortfolioChanges> portfolioChangesPerAssetName = portfolioChangeCalculator.calculate(
                currentPortfolios,
                dayBeforePortfolios,
                weekBeforePortfolios,
                monthBeforePortfolios
        );

        List<PortfolioItemResponse> items = currentPortfolios.stream()
                .map(portfolio -> PortfolioItemResponse.builder()
                        .assetName(portfolio.getAssetName())
                        .assetType(portfolio.getAssetType())
                        .amount(portfolio.getAmount())
                        .currentValue(portfolio.getComputedValue())
                        .dailyPercentageChange(portfolioChangesPerAssetName.get(portfolio.getAssetName()).getDayBeforePercentageChange())
                        .weeklyPercentageChange(portfolioChangesPerAssetName.get(portfolio.getAssetName()).getWeekBeforePercentageChange())
                        .monthlyPercentageChange(portfolioChangesPerAssetName.get(portfolio.getAssetName()).getMonthBeforePercentageChange())
                        .dailyValueChange(portfolioChangesPerAssetName.get(portfolio.getAssetName()).getDayBeforeValueChange())
                        .weeklyValueChange(portfolioChangesPerAssetName.get(portfolio.getAssetName()).getWeekBeforeValueChange())
                        .monthlyValueChange(portfolioChangesPerAssetName.get(portfolio.getAssetName()).getMonthBeforeValueChange())
                        .currency(portfolio.getCurrency())
                        .build()
                )
                .toList();

        BigDecimal currentPortfolioBalance = calculateBalance(currentPortfolios);
        BigDecimal dayBeforePortfolioBalance = calculateBalance(dayBeforePortfolios);
        BigDecimal weekBeforePortfolioBalance = calculateBalance(weekBeforePortfolios);
        BigDecimal monthBeforePortfolioBalance = calculateBalance(monthBeforePortfolios);

        return PortfolioResponse.builder()
                .portfolioCurrency(currency)
                .portfolioBalance(currentPortfolioBalance)
                .dayBeforePortfolioBalance(dayBeforePortfolioBalance)
                .weekBeforePortfolioBalance(weekBeforePortfolioBalance)
                .monthBeforePortfolioBalance(monthBeforePortfolioBalance)
                .portfolioItems(items)
                .build();
    }

    private BigDecimal calculateBalance(List<PortfolioEod> portfolioEods) {
        return portfolioEods.stream()
                .map(PortfolioEod::getComputedValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
