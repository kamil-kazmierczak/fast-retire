package fast.retire.application.userportfolio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPortfolioRepository extends JpaRepository<UserPortfolio, String> {

    UserPortfolio getUserPortfolioByUser_Id(String userId);

}
