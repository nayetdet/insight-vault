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

    private String username;

    @Override
    public Specification<Insight> getSpecification() {
        Specification<Insight> specs = super.getSpecification();
        if (StringUtils.isNotBlank(username)) specs = specs.and(usernameContains(username));
        return specs;
    }

    public InsightQuery() {
        super(Map.of(
                "id", "id",
                "username", "username",
                "createdAt", "createdAt",
                "updatedAt", "updatedAt"
        ));
    }

    @Override
    @Schema(
            defaultValue = "id",
            allowableValues = {
                    "id",
                    "username",
                    "createdAt",
                    "updatedAt",
                    "-id",
                    "-username",
                    "-createdAt",
                    "-updatedAt"
            }
    )
    public String getOrderBy() {
        return super.getOrderBy();
    }

}
