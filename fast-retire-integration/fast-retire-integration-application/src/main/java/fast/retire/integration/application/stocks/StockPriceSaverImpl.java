package fast.retire.integration.application.stocks;

import fast.retire.api.History;
import fast.retire.api.HistoryRepository;
import fast.retire.api.register.HistoryRegister;
import fast.retire.api.register.HistoryRegisterRepository;
import fast.retire.api.register.Price;
import fast.retire.integration.api.stocks.StockPriceSaver;
import fast.retire.integration.api.stocks.StockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class StockPriceSaverImpl implements StockPriceSaver {

    private final HistoryRegisterRepository historyRegisterRepository;
    private final HistoryRepository historyRepository;

    @Transactional
    public void save(StockResponse response) {
        String symbol = response.getSymbol();

        var fetched = response.getPricePerDate().entrySet().stream()
                .map(entry -> History.builder()
                        .id(symbol + "_" + entry.getKey())
                        .price(entry.getValue())
                        .priceCurrency("USD") // alphaVantage always return prices in USD for stocks
                        .registerDate(entry.getKey())
                        .asset(symbol)
                        .build())
                .toList();

        List<String> idsInRegister = historyRegisterRepository.findAll().stream()
                .map(HistoryRegister::getId)
                .toList();

        List<History> toSave = fetched.stream()
                .filter(fetchedItem -> !idsInRegister.contains(fetchedItem.getId()))
                .toList();

        List<HistoryRegister> toSaveInRegister = toSave.stream()
                .map(history -> HistoryRegister.builder()
                        .id(history.getId())
                        .price(Price.builder()
                                .currency(history.getPriceCurrency())
                                .value(history.getPrice())
                                .build())
                        .asset(history.getAsset())
                        .registerDate(history.getRegisterDate())
                        .build())
                .toList();

        historyRegisterRepository.saveAll(toSaveInRegister);
        historyRepository.saveAll(toSave);
    }
}
