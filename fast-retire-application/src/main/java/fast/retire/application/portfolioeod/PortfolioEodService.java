package fast.retire.application.portfolioeod;

import fast.retire.application.portfolioeod.generator.PortfolioEodGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PortfolioEodService {

    private final PortfolioEodRepository portfolioEodRepository;
    private final PortfolioEodGenerator portfolioEodGenerator;

    public List<PortfolioEod> getPortfolioEodByUserIdAndDate(String userId, String currency, LocalDate date) {
        return portfolioEodRepository.getPortfolioEodsByUser_IdAndCurrencyAndDate(userId, currency, date);
    }

    public List<PortfolioEod> getPortfolioEodsByUserIdFromDateTillNow(String userId, String currency, String assetName) {
        return portfolioEodRepository.getPortfolioEodsByUser_IdAndCurrencyAndAssetName(userId, currency, assetName, Sort.by("date"));
    }

    public List<PortfolioSummary> getPortfolioSummary(String userId, String currency, String assetType) {
        List<PortfolioEod> portfolioEods
                = portfolioEodRepository.getPortfolioEodsByUser_IdAndCurrencyAndAssetType(userId, currency, assetType, Sort.by("date"));

        Map<LocalDate, BigDecimal> portfoliosPerDate = portfolioEods.stream()
                .collect(Collectors.groupingBy(
                        PortfolioEod::getDate,
                                Collectors.reducing(BigDecimal.ZERO, PortfolioEod::getComputedValue, BigDecimal::add)
                        ));

        return portfoliosPerDate.entrySet().stream()
                .map(entry -> PortfolioSummary.builder()
                        .userId(userId)
                        .assetType(assetType)
                        .date(entry.getKey())
                        .computedValue(entry.getValue())
                        .currency(currency)
                        .build())
                .toList();
    }

    @Transactional
    public void regeneratePortfolio(String userId, String targetCurrency) {
        portfolioEodGenerator.generate(userId, targetCurrency);
    }
}
