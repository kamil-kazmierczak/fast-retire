package fast.retire.application.userportfolio;

import fast.retire.application.assetaction.AssetAction;
import fast.retire.application.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "USER_PORTFOLIO")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserPortfolio {

    @Id
    private String id;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "userPortfolio")
    private List<AssetAction> assetActions;
}
