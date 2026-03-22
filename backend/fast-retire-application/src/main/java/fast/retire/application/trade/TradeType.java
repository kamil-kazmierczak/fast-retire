package fast.retire.application.trade;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TradeType {
    BUY("BUY"),
    SELL("SELL");

    private final String value;
}
