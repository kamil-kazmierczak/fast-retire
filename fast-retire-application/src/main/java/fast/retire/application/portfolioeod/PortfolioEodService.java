package fast.retire.application.portfolioeod;

import fast.retire.application.portfolioeod.generator.PortfolioEodGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

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

    @Transactional
    public void regeneratePortfolio(String userId, String targetCurrency) {
        portfolioEodGenerator.generate(userId, targetCurrency);
    }
}
