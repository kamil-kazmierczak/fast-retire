package fast.retire.application.history;

import fast.retire.api.History;
import fast.retire.api.HistoryRepository;
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


    @GetMapping("/{asset}")
    public List<History> findByAsset(@PathVariable("asset") String asset) {
        Page<History> results = historyRepository.findHistoryByAsset(asset, PageRequest.of(1, 10));
        return results.get().toList();
    }

}