package fast.retire.application.history;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, String> {

    Optional<History> getHistoryByAssetAndRegisterDate(String asset, LocalDate registerDate);

    List<History> getAllByAssetAndAndPrice_Currency(String asset, String currency);

}
