package fast.retire.integration.application.currencyrates;

import fast.retire.application.currencyrates.CurrencyRate;
import fast.retire.application.currencyrates.CurrencyRateRepository;
import fast.retire.integration.api.fxrates.CurrencyRateResponse;
import fast.retire.integration.api.fxrates.CurrencyRateSaver;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
public class CurrencyRateSaverImpl implements CurrencyRateSaver {

    private final CurrencyRateRepository currencyRateRepository;

    @Transactional
    public void save(CurrencyRateResponse response) {
        String baseCurrency = response.getBaseCurrency();
        String targetCurrency = response.getTargetCurrency();

        var fetched = response.getPricePerDate().entrySet().stream()
                .map(entry -> CurrencyRate.builder()
                        .id(UUID.randomUUID().toString())
                        .date(entry.getKey())
                        .baseCurrency(baseCurrency)
                        .targetCurrency(targetCurrency)
                        .amount(entry.getValue())
                        .build())
                .toList();

        var registeredAlreadyDates = currencyRateRepository
                .findAllByBaseCurrencyAndTargetCurrency(baseCurrency, targetCurrency).stream()
                .map(CurrencyRate::getDate)
                .toList();

        var toBeSaved = fetched.stream()
                .filter(fetchedItem -> !registeredAlreadyDates.contains(fetchedItem.getDate()))
                .toList();

        currencyRateRepository.saveAll(toBeSaved);
    }
}
