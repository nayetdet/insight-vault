package io.github.nayetdet.insightvault.repository.query.jpa.specification;

import io.github.nayetdet.insightvault.model.Insight;
import org.springframework.data.jpa.domain.Specification;

public class InsightSpecificationFactory {

    private InsightSpecificationFactory() {
    }

    public static Specification<Insight> usernameContains(String username) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("username")), "%" + username.toUpperCase() + "%");
    }

    public static Specification<Insight> recordIdEquals(String recordId) {
        return (root, query, cb) ->
                cb.equal(root.get("recordId"), recordId);
    }

}
