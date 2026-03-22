package fast.retire.application.trade;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;

@Embeddable
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Asset {

    @Column(name = "ASSET_NAME")
    private String name;

    @Column(name = "ASSET_TYPE")
    @Enumerated(EnumType.STRING)
    private AssetType type;
}
