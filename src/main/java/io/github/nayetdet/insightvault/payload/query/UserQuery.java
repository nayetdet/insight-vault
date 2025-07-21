package io.github.nayetdet.insightvault.payload.query;

import io.github.nayetdet.insightvault.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

import static io.github.nayetdet.insightvault.repository.query.jpa.specification.UserSpecificationFactory.*;

@Getter
@Setter
public class UserQuery extends BaseJpaSpecificationQuery<User> {

    private String username;
    private String name;

    @Override
    public Specification<User> getSpecification() {
        Specification<User> specs = super.getSpecification();
        if (StringUtils.isNotBlank(username)) specs = specs.and(usernameContains(username));
        if (StringUtils.isNotBlank(name)) specs = specs.and(nameContains(name));
        return specs;
    }

    public UserQuery() {
        super(Map.of(
            "id", "id",
            "username", "username",
            "name", "name",
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
                    "name",
                    "createdAt",
                    "updatedAt",
                    "-id",
                    "-username",
                    "-name",
                    "-createdAt",
                    "-updatedAt"
            }
    )
    public String getOrderBy() {
        return super.getOrderBy();
    }

}
