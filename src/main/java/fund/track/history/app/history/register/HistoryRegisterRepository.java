package fund.track.history.app.history.register;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HistoryRegisterRepository extends JpaRepository<HistoryRegister, String> {

    Optional<HistoryRegister> findFirstByTicker(String ticker);

}
