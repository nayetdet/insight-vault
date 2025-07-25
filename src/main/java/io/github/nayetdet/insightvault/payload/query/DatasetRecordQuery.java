package io.github.nayetdet.insightvault.payload.query;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

import static io.github.nayetdet.insightvault.repository.query.elasticsearch.DatasetRecordQueryFactory.*;

@Getter
@Setter
public class DatasetRecordQuery extends BaseElasticsearchQuery {

    private Long datasetId;
    private String keyword;

    public DatasetRecordQuery() {
        super(Map.of(
                "id", "id",
                "datasetId", "datasetId",
                "createdAt", "createdAt",
                "updatedAt", "updatedAt"
        ));
    }

    @Override
    protected List<Query> getQueryList() {
        List<Query> queries = super.getQueryList();
        if (datasetId != null) queries.add(datasetIdEquals(datasetId));
        if (StringUtils.isNotBlank(keyword)) queries.add(keywordContains(keyword));
        return queries;
    }

    @Override
    @Schema(
            defaultValue = "id",
            allowableValues = {
                    "id",
                    "datasetId",
                    "createdAt",
                    "updatedAt",
                    "-id",
                    "-datasetId",
                    "-createdAt",
                    "-updatedAt"
            }
    )
    public String getOrderBy() {
        return super.getOrderBy();
    }

}
