package fast.retire.application.portfolioeod;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioEodRepository extends JpaRepository<PortfolioEod, String> {

    List<PortfolioEod> getPortfolioEodsByUser_Id(String userId, Sort sort);

    void deletePortfolioEodsByUser_Id(String userId);

}
