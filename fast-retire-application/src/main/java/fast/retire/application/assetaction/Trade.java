package fast.retire.application.assetaction;

import fast.retire.application.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "TRADES")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Trade {

    @Id
    private String id;

    private String asset;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TradeType tradeType;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
