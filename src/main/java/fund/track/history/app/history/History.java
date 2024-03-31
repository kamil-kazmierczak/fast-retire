package fund.track.history.app.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(indexName = "history")
@Getter
@AllArgsConstructor
@ToString
@Builder
public class History {

    @Id
    String id;

    @Field(type = FieldType.Text)
    String ticker;

    @Field(type = FieldType.Double)
    BigDecimal price;

    @Field(type = FieldType.Date)
    LocalDate date;

}
