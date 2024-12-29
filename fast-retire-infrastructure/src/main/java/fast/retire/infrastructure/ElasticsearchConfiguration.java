package fast.retire.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "fast.retire.api")
public class ElasticsearchConfiguration extends org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
       return ClientConfiguration.builder()
               .connectedTo("localhost:9200")
               .build();

    }
}