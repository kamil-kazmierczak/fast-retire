package fast.retire.infrastructure;

import fast.retire.infrastructure.filters.TraceIdResponseFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FiltersConfiguration {

    @Bean
    public TraceIdResponseFilter traceIdResponseFilter() {
        return new TraceIdResponseFilter();
    }
}
