package fast.retire.integration.application.cryptocurrencies;

import fast.retire.application.history.History;
import fast.retire.application.history.HistoryRepository;
import fast.retire.application.history.Price;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyPriceSaver;
import fast.retire.integration.api.cryptocurrencies.CryptocurrencyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CryptocurrencyPriceSaverImpl implements CryptocurrencyPriceSaver {

    private final HistoryRepository historyRepository;

    @Transactional
    public void save(CryptocurrencyResponse response) {
        String symbol = response.getSymbol();
        String currency = response.getCurrency();

        var fetched = response.getPricePerDate().entrySet().stream()
                .map(entry -> History.builder()
                        .id(UUID.randomUUID().toString())
                        .price(new Price(entry.getValue(), currency))
                        .registerDate(entry.getKey())
                        .asset(symbol)
                        .build())
                .toList();

        Set<LocalDate> existedHistoriesDates = historyRepository.getAllByAssetAndAndPrice_Currency(symbol, currency).stream()
                .map(History::getRegisterDate)
                .collect(Collectors.toSet());

        List<History> toSave = fetched.stream()
                .filter(fetchedItem -> !existedHistoriesDates.contains(fetchedItem.getRegisterDate()))
                .toList();

        historyRepository.saveAll(toSave);
    }
}
