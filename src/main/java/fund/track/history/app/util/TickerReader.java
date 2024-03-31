package fund.track.history.app.util;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@NoArgsConstructor
@Log4j2
public class TickerReader {

    public List<String> read(String pathString) {
        Path path = Paths.get(pathString);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            log.error("Problem with reading file...", e);
            throw new RuntimeException(e);
        }
    }

}
