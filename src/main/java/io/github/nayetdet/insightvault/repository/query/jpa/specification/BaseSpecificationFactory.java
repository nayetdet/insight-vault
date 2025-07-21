package io.github.nayetdet.insightvault.repository.query.jpa.specification;

import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class BaseSpecificationFactory {

    private BaseSpecificationFactory() {
    }

    public static <T> Specification<T> createdBefore(LocalDate createdBefore) {
        return (root, query, cb) -> {
            Expression<LocalDate> createdAtDate = cb.function("DATE", LocalDate.class, root.get("createdAt"));
            return cb.lessThanOrEqualTo(createdAtDate, createdBefore);
        };
    }

    public static <T> Specification<T> createdAfter(LocalDate createdAfter) {
        return (root, query, cb) -> {
            Expression<LocalDate> createdAtDate = cb.function("DATE", LocalDate.class, root.get("createdAt"));
            return cb.greaterThanOrEqualTo(createdAtDate, createdAfter);
        };
    }

}
