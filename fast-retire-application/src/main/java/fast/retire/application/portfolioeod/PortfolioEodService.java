package fast.retire.application.portfolioeod;

import fast.retire.application.portfolioeod.generator.PortfolioEodGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class PortfolioEodService {

    private final PortfolioEodRepository portfolioEodRepository;
    private final PortfolioEodGenerator portfolioEodGenerator;

    public List<PortfolioEod> getUserPortfolioEodByUserId(String userId) {
        return portfolioEodRepository.getPortfolioEodsByUser_Id(userId, Sort.by("date").ascending());
    }

    @Transactional
    public void regeneratePortfolio(String userId, String targetCurrency) {
        portfolioEodGenerator.generate(userId, targetCurrency);
    }
}
