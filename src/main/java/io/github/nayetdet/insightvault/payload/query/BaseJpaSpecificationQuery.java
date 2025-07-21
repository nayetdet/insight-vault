package io.github.nayetdet.insightvault.payload.query;

import io.github.nayetdet.insightvault.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

import static io.github.nayetdet.insightvault.repository.query.jpa.specification.BaseSpecificationFactory.*;

@Getter
@Setter
public abstract class BaseJpaSpecificationQuery<T extends BaseModel> extends BaseQuery {

    protected BaseJpaSpecificationQuery() {
    }

    protected BaseJpaSpecificationQuery(Map<String, String> sortableFields) {
        super(sortableFields);
    }

    public Specification<T> getSpecification() {
        Specification<T> specs = (root, query, cb) -> cb.conjunction();
        if (getCreatedBefore() != null) specs = specs.and(createdBefore(getCreatedBefore()));
        if (getCreatedAfter() != null) specs = specs.and(createdAfter(getCreatedAfter()));
        return specs;
    }

}
