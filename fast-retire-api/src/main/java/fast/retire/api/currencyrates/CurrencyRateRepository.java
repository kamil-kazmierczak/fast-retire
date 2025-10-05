package fast.retire.api.currencyrates;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, String> {

    List<CurrencyRate> findAllByBaseCurrencyAndTargetCurrency(String baseCurrency, String targetCurrency);
    Optional<CurrencyRate> findCurrencyRateByBaseCurrencyAndTargetCurrencyAndDate(
            String baseCurrency, String targetCurrency, LocalDate date);


}
