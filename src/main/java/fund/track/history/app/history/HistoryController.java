package fund.track.history.app.history;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
@Log4j2
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryRepository historyRepository;


    @GetMapping
    public List<History> findByTicker() {
        Page<History> ibm = historyRepository.findHistoryByTicker("IBM", PageRequest.of(1, 10));
        return ibm.get().toList();
    }

}