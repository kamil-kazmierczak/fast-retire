package fast.retire.integration.application.stocks;

import fast.retire.application.register.HistoryRegister;
import fast.retire.application.register.HistoryRegisterRepository;
import fast.retire.application.register.Price;
import fast.retire.integration.api.stocks.StockPriceSaver;
import fast.retire.integration.api.stocks.StockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class StockPriceSaverImpl implements StockPriceSaver {

    private final HistoryRegisterRepository historyRegisterRepository;

    @Transactional
    public void save(StockResponse response) {
        String symbol = response.getSymbol();

        var fetched = response.getPricePerDate().entrySet().stream()
                .map(entry -> HistoryRegister.builder()
                        .id(symbol + "_" + entry.getKey())
                        .price(new Price(entry.getValue(), "USD"))
                        .registerDate(entry.getKey())
                        .asset(symbol)
                        .build())
                .toList();

        List<String> idsInRegister = historyRegisterRepository.findAll().stream()
                .map(HistoryRegister::getId)
                .toList();

        List<HistoryRegister> toSave = fetched.stream()
                .filter(fetchedItem -> !idsInRegister.contains(fetchedItem.getId()))
                .toList();

        historyRegisterRepository.saveAll(toSave);
    }
}
