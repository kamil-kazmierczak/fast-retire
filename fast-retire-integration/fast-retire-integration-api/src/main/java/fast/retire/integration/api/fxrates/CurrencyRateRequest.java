package fast.retire.integration.api.fxrates;

import lombok.*;


@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRateRequest {

    private String baseCurrency;
    private String targetCurrency;

}