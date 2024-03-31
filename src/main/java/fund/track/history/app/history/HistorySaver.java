package fund.track.history.app.history;

import fund.track.history.app.history.register.HistoryRegister;
import fund.track.history.app.history.register.HistoryRegisterRepository;
import fund.track.history.app.stock.StockResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class HistorySaver {

    private final HistoryRegisterRepository historyRegisterRepository;
    private final HistoryRepository historyRepository;

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
