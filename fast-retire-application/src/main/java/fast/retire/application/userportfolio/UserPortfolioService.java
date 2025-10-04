package fast.retire.application.userportfolio;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserPortfolioService {

    private final UserPortfolioRepository userPortfolioRepository;

    public UserPortfolio getUserPortfolioByUserId(String userId) {
        return userPortfolioRepository.getUserPortfolioByUser_Id(userId);
    }
}
