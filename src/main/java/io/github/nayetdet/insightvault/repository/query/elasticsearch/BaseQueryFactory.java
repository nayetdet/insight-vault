package io.github.nayetdet.insightvault.repository.query.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BaseQueryFactory {

    private BaseQueryFactory() {
    }

    public static Query createdBefore(LocalDate createdBefore) {
        return Query.of(q -> q
                .range(r -> r
                        .date(d -> d
                                .field("createdAt")
                                .lte(createdBefore.atTime(LocalTime.MAX).format(DateTimeFormatter.ISO_DATE_TIME))
                        )
                )
        );
    }

    public static Query createdAfter(LocalDate createdAfter) {
        return Query.of(q -> q
                .range(r -> r
                        .date(d -> d
                                .field("createdAt")
                                .gte(createdAfter.atStartOfDay().format(DateTimeFormatter.ISO_DATE_TIME))
                        )
                )
        );
    }

}
