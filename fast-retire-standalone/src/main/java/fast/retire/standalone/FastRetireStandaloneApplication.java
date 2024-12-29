package fast.retire.standalone;

import fast.retire.infrastructure.BeanConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        BeanConfiguration.class,
})
public class FastRetireStandaloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastRetireStandaloneApplication.class, args);
    }

}