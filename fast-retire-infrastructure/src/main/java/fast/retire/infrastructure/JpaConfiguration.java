package fast.retire.infrastructure;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
        "fast.retire.api.register",
        "fast.retire.application"
})
@EntityScan({
        "fast.retire.api.register",
        "fast.retire.application"
})
public class JpaConfiguration {


}
