package io.github.nayetdet.insightvault.payload.query;

import io.github.nayetdet.insightvault.model.Dataset;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

import static io.github.nayetdet.insightvault.repository.query.jpa.specification.DatasetSpecificationFactory.*;

@Getter
@Setter
public class DatasetQuery extends BaseJpaSpecificationQuery<Dataset> {

    private String recordId;
    private String name;
    private String contentType;
    private Long sizeInBytesMin;
    private Long sizeInBytesMax;
    private String username;

    public DatasetQuery() {
        super(Map.of(
                "id", "id",
                "recordId", "recordId",
                "name", "name",
                "contentType", "contentType",
                "sizeInBytes", "sizeInBytes",
                "username", "user.username",
                "createdAt", "createdAt",
                "updatedAt", "updatedAt"
        ));
    }

    @Override
    public Specification<Dataset> getSpecification() {
        Specification<Dataset> specs = super.getSpecification();
        if (StringUtils.isNotBlank(recordId)) specs = specs.and(recordIdEquals(recordId));
        if (StringUtils.isNotBlank(name)) specs = specs.and(nameContains(name));
        if (StringUtils.isNotBlank(contentType)) specs = specs.and(contentTypeEquals(contentType));
        if (sizeInBytesMin != null) specs = specs.and(sizeInBytesMin(sizeInBytesMin));
        if (sizeInBytesMax != null) specs = specs.and(sizeInBytesMax(sizeInBytesMax));
        if (StringUtils.isNotBlank(username)) specs = specs.and(usernameEquals(username));
        return specs;
    }

    @Override
    @Schema(
            defaultValue = "id",
            allowableValues = {
                    "id",
                    "recordId",
                    "name",
                    "contentType",
                    "sizeInBytes",
                    "username",
                    "createdAt",
                    "updatedAt",
                    "-id",
                    "-recordId",
                    "-name",
                    "-contentType",
                    "-sizeInBytes",
                    "-username",
                    "-createdAt",
                    "-updatedAt"
            }
    )
    public String getOrderBy() {
        return super.getOrderBy();
    }

}
