package fast.retire.application.portfolioeod;

import fast.retire.application.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "PORTFOLIO_EOD")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class PortfolioEod {

    @Id
    private String id;

    private LocalDate date;

    private BigDecimal amount;

    private String assetName;
    private String assetType;

    private BigDecimal computedValue;
    private String currency;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
