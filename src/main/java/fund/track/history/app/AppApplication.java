package fund.track.history.app;

import fund.track.history.app.history.History;
import fund.track.history.app.stock.StockFetcher;
import fund.track.history.app.stock.StockResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
@Log4j2
public class AppApplication implements CommandLineRunner {

    private final StockFetcher stockFetcher;
    private final ElasticsearchOperations elasticsearchOperations;


    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        StockResponse response = stockFetcher.fetch();

        String symbol = response.getTicker();

        var list = response.getPricePerMonth().entrySet().stream()
                .map(entry -> new History(UUID.randomUUID().toString(), symbol, entry.getValue(), entry.getKey()))
                .toList();

        elasticsearchOperations.save(list);

        log.debug("HISTORY SAVED");

    }
}
