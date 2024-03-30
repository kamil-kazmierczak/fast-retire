package fund.track.history.app.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface HistoryRepository extends ElasticsearchRepository<History, String> {

    Page<History> findHistoryByTicker(String ticker, Pageable pageable);

}
