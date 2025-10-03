package fast.retire.integration.application.cryptocurrencies;

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

    @Transactional
    public void save(CryptocurrencyResponse response) {
        String symbol = response.getSymbol();
        String currency = response.getCurrency();

        var fetched = response.getPricePerDate().entrySet().stream()
                .map(entry -> HistoryRegister.builder()
                        .id(symbol + "_" + entry.getKey())
                        .price(new Price(entry.getValue(), currency))
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
