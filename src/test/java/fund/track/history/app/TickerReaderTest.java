package fund.track.history.app;

import fund.track.history.app.util.TickerReader;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
class TickerReaderTest {

	private final TickerReader tickerReader;


	@Test
	void tickerTest() {
		// when
		List<String> lines = tickerReader.read("src/test/resources/test.txt");

		// then
		Assertions.assertLinesMatch(List.of("IBM", "AAPL", "DNOPL"), lines);
	}

}
