package fast.retire.api.register;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Embeddable
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Price {

    @Column(name = "PRICE_VALUE")
    private BigDecimal value;

    @Column(name = "PRICE_CURRENCY")
    private String currency;

}