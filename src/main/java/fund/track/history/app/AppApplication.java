package fund.track.history.app;

import fund.track.history.app.history.History;
import fund.track.history.app.history.HistoryRepository;
import fund.track.history.app.history.register.HistoryRegister;
import fund.track.history.app.history.register.HistoryRegisterRepository;
import fund.track.history.app.stock.StockFetcher;
import fund.track.history.app.stock.StockResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
@Log4j2
public class AppApplication implements CommandLineRunner {

    private final StockFetcher stockFetcher;
    private final HistoryRepository historyRepository;
    private final HistoryRegisterRepository historyRegisterRepository;


    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        StockResponse response = stockFetcher.fetch();
        String symbol = response.getTicker();

        var list = response.getPricePerMonth().entrySet().stream()
                .map(entry -> History.builder()
                        .id(symbol + "_" + entry.getKey())
                        .price(entry.getValue())
                        .date(entry.getKey())
                        .ticker(symbol)
                        .build())
                .toList();

        List<HistoryRegister> toSave = list.stream()
                .map(history -> HistoryRegister.builder()
                        .id(history.getId())
                        .price(history.getPrice())
                        .ticker(history.getTicker())
                        .date(history.getDate())
                        .build())
                .toList();

        historyRegisterRepository.saveAll(toSave);

        historyRepository.saveAll(list);

        log.debug("HISTORY SAVED");

    }
}
