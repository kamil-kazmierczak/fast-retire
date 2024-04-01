package ft.history.application.history;

import ft.history.api.History;
import ft.history.api.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
@Log4j2
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryRepository historyRepository;


    @GetMapping("/{ticker}")
    public List<History> findByTicker(@PathVariable("ticker") String ticker) {
        Page<History> results = historyRepository.findHistoryByTicker(ticker, PageRequest.of(1, 10));
        return results.get().toList();
    }

}