package fast.retire.infrastructure;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
        "fast.retire.application"
})
@EntityScan({
        "fast.retire.application"
})
public class JpaConfiguration {


}
