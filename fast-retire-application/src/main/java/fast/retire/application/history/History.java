package fast.retire.application.history;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "HISTORY")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class History {

    @Id
    private String id;

    private String asset;

    private LocalDate registerDate;

    @Embedded
    private Price price;

}