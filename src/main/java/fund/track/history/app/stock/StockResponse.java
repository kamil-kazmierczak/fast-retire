package fund.track.history.app.stock;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.Map;

@Builder
@Value
public class StockResponse {

    String ticker;
    Map<LocalDate, String> pricePerMonth;

}
