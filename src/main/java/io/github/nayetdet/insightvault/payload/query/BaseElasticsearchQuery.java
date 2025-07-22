package io.github.nayetdet.insightvault.payload.query;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.github.nayetdet.insightvault.repository.query.elasticsearch.BaseQueryFactory.createdAfter;
import static io.github.nayetdet.insightvault.repository.query.elasticsearch.BaseQueryFactory.createdBefore;

@Getter
@Setter
public abstract class BaseElasticsearchQuery extends BaseQuery {

    protected BaseElasticsearchQuery() {
    }

    protected BaseElasticsearchQuery(Map<String, String> sortableFields) {
        super(sortableFields);
    }

    public Query getQuery() {
        List<Query> queries = getQueryList();
        return queries.isEmpty()
                ? Query.of(q -> q.matchAll(m -> m))
                : Query.of(q -> q.bool(b -> b.must(queries)));
    }

    protected List<Query> getQueryList() {
        List<Query> queries = new ArrayList<>();
        if (getCreatedBefore() != null) queries.add(createdBefore(getCreatedBefore()));
        if (getCreatedAfter() != null) queries.add(createdAfter(getCreatedAfter()));
        return queries;
    }

}
