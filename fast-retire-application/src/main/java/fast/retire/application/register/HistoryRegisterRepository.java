package fast.retire.application.register;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface HistoryRegisterRepository extends JpaRepository<HistoryRegister, String> {

    Optional<HistoryRegister> getHistoryRegisterByAssetAndRegisterDate(String asset, LocalDate registerDate);

}
