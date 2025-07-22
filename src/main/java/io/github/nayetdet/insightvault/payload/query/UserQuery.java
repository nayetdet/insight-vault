package io.github.nayetdet.insightvault.payload.query;

import io.github.nayetdet.insightvault.model.User;
import io.github.nayetdet.insightvault.model.enums.UserRole;
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
    private UserRole role;

    public UserQuery() {
        super(Map.of(
            "id", "id",
            "username", "username",
            "name", "name",
            "role", "role",
            "createdAt", "createdAt",
            "updatedAt", "updatedAt"
        ));
    }

    @Override
    public Specification<User> getSpecification() {
        Specification<User> specs = super.getSpecification();
        if (StringUtils.isNotBlank(username)) specs = specs.and(usernameContains(username));
        if (StringUtils.isNotBlank(name)) specs = specs.and(nameContains(name));
        if (role != null) specs = specs.and(roleEquals(role));
        return specs;
    }

    @Override
    @Schema(
            defaultValue = "id",
            allowableValues = {
                    "id",
                    "username",
                    "name",
                    "role",
                    "createdAt",
                    "updatedAt",
                    "-id",
                    "-username",
                    "-name",
                    "-role",
                    "-createdAt",
                    "-updatedAt"
            }
    )
    public String getOrderBy() {
        return super.getOrderBy();
    }

}
