package fast.retire.integration.infrastructure.userportfolio;

import fast.retire.application.userportfolio.UserPortfolio;
import fast.retire.application.userportfolio.UserPortfolioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userportfolio")
@Log4j2
@RequiredArgsConstructor
public class UserPortfolioController {

    private final UserPortfolioService userPortfolioService;


    @GetMapping("/{userId}")
    public UserPortfolioResponse getUserPortfolio(@PathVariable String userId) {
        UserPortfolio userPortfolio = userPortfolioService.getUserPortfolioByUserId(userId);

        return UserPortfolioResponse.builder()
                .id(userPortfolio.getId())
                .build();
    }
}
