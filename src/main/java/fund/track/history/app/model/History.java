package fund.track.history.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "history")
@Getter
@AllArgsConstructor
@ToString
public class History {

    @Id
    String id;

    @Field(type = FieldType.Integer)
    Integer from;

    @Field(type = FieldType.Integer)
    Integer to;

    String name;
}
