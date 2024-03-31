package fund.track.history.app;

import fund.track.history.app.history.History;
import fund.track.history.app.history.HistoryRepository;
import fund.track.history.app.history.register.HistoryRegister;
import fund.track.history.app.history.register.HistoryRegisterRepository;
import fund.track.history.app.stock.StockFetcher;
import fund.track.history.app.stock.StockResponse;
import fund.track.history.app.util.TickerReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootApplication
@RequiredArgsConstructor
@Log4j2
public class AppApplication implements CommandLineRunner {

    private final StockFetcher stockFetcher;
    private final HistoryRepository historyRepository;
    private final HistoryRegisterRepository historyRegisterRepository;
    private final TickerReader tickerReader;


    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var tickers = tickerReader.read("src/main/resources/stocks.txt");
        boolean savedNewRecords = false;

        Map<String, Boolean> tickerExistenceInRegister = tickers.stream()
                .collect(Collectors.toMap(Function.identity(),
                        e -> historyRegisterRepository.findFirstByTicker(e).isPresent()));

        for (var entry : tickerExistenceInRegister.entrySet()) {
            if (!entry.getValue()) {
                StockResponse response = stockFetcher.fetch(entry.getKey());
                save(response);
                savedNewRecords = true;
            }
        }

        if (savedNewRecords) {
            log.debug("NEW RECORDS SAVED!!!");
        } else {
            log.debug("NOTHING NEW SAVED");
        }

    }

    private void save(StockResponse response) {
        String symbol = response.getTicker();

        var fetched = response.getPricePerMonth().entrySet().stream()
                .map(entry -> History.builder()
                        .id(symbol + "_" + entry.getKey())
                        .price(entry.getValue())
                        .date(entry.getKey())
                        .ticker(symbol)
                        .build())
                .toList();

        List<String> idsInRegister = historyRegisterRepository.findAll().stream()
                .map(HistoryRegister::getId)
                .toList();

        List<History> toSave = fetched.stream()
                .filter(fetchedItem -> !idsInRegister.contains(fetchedItem.getId()))
                .toList();

        List<HistoryRegister> toSaveInRegister = toSave.stream()
                .map(history -> HistoryRegister.builder()
                        .id(history.getId())
                        .price(history.getPrice())
                        .ticker(history.getTicker())
                        .date(history.getDate())
                        .build())
                .toList();

        historyRegisterRepository.saveAll(toSaveInRegister);
        historyRepository.saveAll(toSave);
    }
}
