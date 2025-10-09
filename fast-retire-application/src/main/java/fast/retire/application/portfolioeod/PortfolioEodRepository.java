package fast.retire.application.portfolioeod;

import fast.retire.application.user.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PortfolioEodRepository extends JpaRepository<PortfolioEod, String> {

    List<PortfolioEod> getPortfolioEodsByUser_IdAndCurrencyAndDate(String userId, String currency, LocalDate date);

    List<PortfolioEod> getPortfolioEodsByUser_IdAndCurrencyAndAssetName(String userId, String currency, String assetName, Sort sort);

    List<PortfolioEod> getPortfolioEodsByUser_IdAndCurrencyAndAssetType(String userId, String currency, String assetType, Sort sort);

    void deletePortfolioEodsByUser_Id(String userId);

    String user(User user);
}
