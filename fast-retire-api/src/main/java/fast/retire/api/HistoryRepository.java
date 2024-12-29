package fast.retire.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface HistoryRepository extends ElasticsearchRepository<History, String> {

    Page<History> findHistoryByAsset(String asset, Pageable pageable);

}
