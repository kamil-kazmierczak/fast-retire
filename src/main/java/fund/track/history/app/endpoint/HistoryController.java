package fund.track.history.app.endpoint;

import fund.track.history.app.model.History;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
@Log4j2
@RequiredArgsConstructor
public class HistoryController {

    private final ElasticsearchOperations elasticsearchOperations;


    @PostMapping
    public History save(@RequestBody History history) {
        log.debug("Saving history document {}", history);
        History savedEntity = elasticsearchOperations.save(history);
        return savedEntity;
    }

    @GetMapping("/{id}")
    public History findById(@PathVariable("id") Long id) {
        return elasticsearchOperations.get(id.toString(), History.class);
    }

}