package fast.retire.integration.application.cryptocurrencies;

import fast.retire.api.History;
import fast.retire.api.HistoryRepository;
import fast.retire.api.register.HistoryRegister;
import fast.retire.api.register.HistoryRegisterRepository;
import fast.retire.api.register.Price;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyPriceSaver;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class CryptocurrencyPriceSaverImpl implements CryptocurrencyPriceSaver {

    private final HistoryRegisterRepository historyRegisterRepository;
    private final HistoryRepository historyRepository;

    @Transactional
    public void save(CryptocurrencyResponse response) {
        String symbol = response.getSymbol();
        String currency = response.getCurrency();

        var fetched = response.getPricePerDate().entrySet().stream()
                .map(entry -> History.builder()
                        .id(symbol + "_" + entry.getKey())
                        .price(entry.getValue())
                        .priceCurrency(currency) // alphaVantage always return prices in USD for stocks
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
