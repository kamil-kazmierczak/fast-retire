package ft.history.standalone;

import ft.history.infrastructure.BeanConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        BeanConfiguration.class,
})
public class FtHistoryStandaloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(FtHistoryStandaloneApplication.class, args);
    }

}