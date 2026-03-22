package fast.retire.integration.api.stocks;

import lombok.*;


@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StockRequest {

    private String symbol;

}