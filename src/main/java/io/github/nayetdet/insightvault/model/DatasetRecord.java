package io.github.nayetdet.insightvault.model;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "records")
public class DatasetRecord implements Serializable {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Long)
    private Long datasetId;

    @Field(type = FieldType.Text)
    private String dataText;

    @Field(type = FieldType.Nested)
    private List<Map<String, Object>> data;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime updatedAt;

}
