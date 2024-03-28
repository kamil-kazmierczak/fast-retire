package fund.track.history.app.endpoint;

import fund.track.history.app.model.History;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
public class HistoryController {

    public HistoryController(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    private ElasticsearchOperations elasticsearchOperations;

    @GetMapping
    public History save() {
        History savedEntity = elasticsearchOperations.save(new History("12", 1,2,"name"));
        return savedEntity;
    }

    @GetMapping("/person/{id}")
    public History findById(@PathVariable("id") Long id) {
        History history = elasticsearchOperations.get(id.toString(), History.class);
        return history;
    }


}
