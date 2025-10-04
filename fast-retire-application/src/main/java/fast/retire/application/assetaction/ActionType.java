package fast.retire.application.assetaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ActionType {
    BUY("BUY"),
    SELL("SELL");

    private final String value;
}
