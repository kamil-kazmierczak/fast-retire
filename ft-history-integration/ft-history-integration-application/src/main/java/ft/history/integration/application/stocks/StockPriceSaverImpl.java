package ft.history.integration.application.stocks;

import ft.history.api.History;
import ft.history.api.HistoryRepository;
import ft.history.api.register.HistoryRegister;
import ft.history.api.register.HistoryRegisterRepository;
import ft.history.integration.api.stocks.StockPriceSaver;
import ft.history.integration.api.stocks.StockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class StockPriceSaverImpl implements StockPriceSaver {

    private final HistoryRegisterRepository historyRegisterRepository;
    private final HistoryRepository historyRepository;

    @Transactional
    public void save(StockResponse response) {
        String symbol = response.getTicker();

        var fetched = response.getPricePerMonth().entrySet().stream()
                .map(entry -> History.builder()
                        .id(symbol + "_" + entry.getKey())
                        .price(entry.getValue())
                        .date(entry.getKey())
                        .ticker(symbol)
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
                        .price(history.getPrice())
                        .ticker(history.getTicker())
                        .date(history.getDate())
                        .build())
                .toList();

        historyRegisterRepository.saveAll(toSaveInRegister);
        historyRepository.saveAll(toSave);
    }
}
