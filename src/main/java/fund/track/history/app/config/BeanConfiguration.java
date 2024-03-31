package fund.track.history.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import fund.track.history.app.history.HistoryRepository;
import fund.track.history.app.history.HistorySaver;
import fund.track.history.app.history.register.HistoryRegisterRepository;
import fund.track.history.app.stock.StockFetcher;
import fund.track.history.app.stock.StockResponse;
import fund.track.history.app.stock.StockResponseDeserializer;
import fund.track.history.app.util.TickerReader;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;

@Configuration
public class BeanConfiguration {

    @Bean
    public StockFetcher stockFetcher(RestTemplate restTemplate, ObjectMapper objectMapper) {
        return new StockFetcher(restTemplate, objectMapper);
    }

    @Bean
    public TickerReader tickerReader() {
        return new TickerReader();
    }

    @Bean
    public HistorySaver historySaver(
            HistoryRegisterRepository historyRegisterRepository,
            HistoryRepository historyRepository) {
        return new HistorySaver(historyRegisterRepository, historyRepository);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(StockResponse.class, new StockResponseDeserializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
    
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        
        return builder -> {
            
            // formatter
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            // deserializers
            builder.deserializers(new LocalDateDeserializer(dateFormatter));
            builder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));
            
            // serializers
            builder.serializers(new LocalDateSerializer(dateFormatter));
            builder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));
        };
    }

}
