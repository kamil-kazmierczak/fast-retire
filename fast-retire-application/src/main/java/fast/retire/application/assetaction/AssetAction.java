package fast.retire.application.assetaction;

import fast.retire.application.userportfolio.UserPortfolio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "ASSET_ACTIONS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AssetAction {

    @Id
    private String id;

    private String asset;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private LocalDate actionDate;

    @ManyToOne
    @JoinColumn(name = "user_portfolio_id")
    private UserPortfolio userPortfolio;

}
