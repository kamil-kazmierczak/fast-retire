package fast.retire.application.user;

import fast.retire.application.trade.Trade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {

    @Id
    private String id;

    private String login;

    private String email;

    @OneToMany(mappedBy = "user")
    private List<Trade> trades = new ArrayList<>();

    private LocalDate registrationDate;

}
