package fast.retire.integration.application.stocks;

import fast.retire.application.history.History;
import fast.retire.application.history.HistoryRepository;
import fast.retire.application.history.Price;
import fast.retire.integration.api.stocks.StockPriceSaver;
import fast.retire.integration.api.stocks.StockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StockPriceSaverImpl implements StockPriceSaver {

    private final HistoryRepository historyRepository;

    @Transactional
    public void save(StockResponse response) {
        String symbol = response.getSymbol();
        String defaultStockCurrency = "USD";

        var fetched = response.getPricePerDate().entrySet().stream()
                .map(entry -> History.builder()
                        .id(UUID.randomUUID().toString())
                        .price(new Price(entry.getValue(), defaultStockCurrency))
                        .registerDate(entry.getKey())
                        .asset(symbol)
                        .build())
                .toList();

        Set<LocalDate> existedDates = historyRepository.getAllByAssetAndAndPrice_Currency(symbol, defaultStockCurrency).stream()
                .map(History::getRegisterDate)
                .collect(Collectors.toSet());

        List<History> toSave = fetched.stream()
                .filter(fetchedItem -> !existedDates.contains(fetchedItem.getRegisterDate()))
                .toList();

        historyRepository.saveAll(toSave);
    }
}
