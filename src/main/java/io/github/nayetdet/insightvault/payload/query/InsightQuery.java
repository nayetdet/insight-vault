package io.github.nayetdet.insightvault.payload.query;

import io.github.nayetdet.insightvault.model.Insight;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

import static io.github.nayetdet.insightvault.repository.query.jpa.specification.InsightSpecificationFactory.*;

@Getter
@Setter
public class InsightQuery extends BaseJpaSpecificationQuery<Insight> {

    private String recordId;
    private String username;

    public InsightQuery() {
        super(Map.of(
                "id", "id",
                "recordId", "recordId",
                "username", "user.username",
                "createdAt", "createdAt",
                "updatedAt", "updatedAt"
        ));
    }

    @Override
    public Specification<Insight> getSpecification() {
        Specification<Insight> specs = super.getSpecification();
        if (StringUtils.isNotBlank(recordId)) specs = specs.and(recordIdEquals(recordId));
        if (StringUtils.isNotBlank(username)) specs = specs.and(usernameContains(username));
        return specs;
    }

    @Override
    @Schema(
            defaultValue = "id",
            allowableValues = {
                    "id",
                    "recordId",
                    "username",
                    "createdAt",
                    "updatedAt",
                    "-id",
                    "-recordId",
                    "-username",
                    "-createdAt",
                    "-updatedAt"
            }
    )
    public String getOrderBy() {
        return super.getOrderBy();
    }

}
