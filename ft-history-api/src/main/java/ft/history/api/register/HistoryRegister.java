package ft.history.api.register;

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
@Table(name = "HISTORY_REGISTER")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HistoryRegister {

    @Id
    private String id;

    private String ticker;

    private LocalDate date;

    private BigDecimal price;
}