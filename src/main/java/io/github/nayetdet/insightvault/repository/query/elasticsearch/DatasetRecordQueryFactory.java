package io.github.nayetdet.insightvault.repository.query.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;

public class DatasetRecordQueryFactory {

    private DatasetRecordQueryFactory() {
    }

    public static Query datasetIdEqual(Long datasetId) {
        return Query.of(q -> q
                .term(t -> t
                        .field("datasetId")
                        .value(datasetId)
                )
        );
    }

    public static Query keywordContains(String keyword) {
        return Query.of(q -> q
                .match(m -> m
                        .field("dataText")
                        .query(keyword)
                )
        );
    }

}
