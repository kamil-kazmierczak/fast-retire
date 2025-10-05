package fast.retire.api.currencyrates;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CURRENCY_RATES")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CurrencyRate {

    @Id
    private String id;

    private String baseCurrency;

    private String targetCurrency;

    private LocalDate date;

    private BigDecimal amount;

}