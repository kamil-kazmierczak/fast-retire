package fast.retire.integration.application.fxrates;

import fast.retire.api.register.HistoryRegister;
import fast.retire.api.register.HistoryRegisterRepository;
import fast.retire.api.register.Price;
import fast.retire.integration.api.fxrates.FxRateResponse;
import fast.retire.integration.api.fxrates.FxRateSaver;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class FxRateSaverImpl implements FxRateSaver {

    private final HistoryRegisterRepository historyRegisterRepository;

    @Transactional
    public void save(FxRateResponse response) {
        String baseCurrency = response.getBaseCurrency();
        String targetCurrency = response.getTargetCurrency();

        var fetched = response.getPricePerDate().entrySet().stream()
                .map(entry -> HistoryRegister.builder()
                        .id(baseCurrency + "_" + targetCurrency + entry.getKey())
                        .price(new Price(entry.getValue(), targetCurrency))
                        .registerDate(entry.getKey())
                        .asset(baseCurrency + "_" + targetCurrency)
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
