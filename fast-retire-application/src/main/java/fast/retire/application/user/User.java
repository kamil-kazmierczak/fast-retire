package fast.retire.application.user;

import fast.retire.api.register.Price;
import fast.retire.application.userportfolio.UserPortfolio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @OneToOne
    private UserPortfolio userPortfolio;

    private LocalDate registrationDate;

}
