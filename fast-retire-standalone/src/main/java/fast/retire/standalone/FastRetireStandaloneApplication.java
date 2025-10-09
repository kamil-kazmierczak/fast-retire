package fast.retire.standalone;

import fast.retire.infrastructure.BeanConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Import({
        BeanConfiguration.class,
})
@EnableScheduling
public class FastRetireStandaloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastRetireStandaloneApplication.class, args);
    }

}