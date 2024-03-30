package fund.track.history.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "fund.track.history.app")
public class EsConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
       return ClientConfiguration.builder()
               .connectedTo("localhost:9200")
               .build();

    }
}