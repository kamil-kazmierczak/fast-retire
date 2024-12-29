package fast.retire.integration.api.cryptocurrencies;

import lombok.*;


@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CryptocurrencyRequest {

    private String symbol;

}