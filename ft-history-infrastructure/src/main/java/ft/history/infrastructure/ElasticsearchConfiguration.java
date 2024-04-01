package ft.history.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "ft.history.api")
public class ElasticsearchConfiguration extends org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
       return ClientConfiguration.builder()
               .connectedTo("localhost:9200")
               .build();

    }
}