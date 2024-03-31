package fund.track.history.app;

import fund.track.history.app.history.HistorySaver;
import fund.track.history.app.history.register.HistoryRegisterRepository;
import fund.track.history.app.stock.StockFetcher;
import fund.track.history.app.stock.StockResponse;
import fund.track.history.app.util.TickerReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootApplication
@RequiredArgsConstructor
@Log4j2
public class AppApplication implements CommandLineRunner {

    private final StockFetcher stockFetcher;
    private final HistoryRegisterRepository historyRegisterRepository;
    private final TickerReader tickerReader;
    private final HistorySaver historySaver;


    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var tickers = tickerReader.read("src/main/resources/stocks.txt");

        Map<String, Boolean> tickerExistenceInRegister = tickers.stream()
                .collect(Collectors.toMap(Function.identity(),
                        e -> historyRegisterRepository.findFirstByTicker(e).isPresent()));

        for (var entry : tickerExistenceInRegister.entrySet()) {
            if (!entry.getValue()) {
                StockResponse response = stockFetcher.fetch(entry.getKey());
                historySaver.save(response);
            }
        }

    }

}