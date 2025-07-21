package io.github.nayetdet.insightvault.payload.query;

import io.github.nayetdet.insightvault.model.Dataset;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatasetQuery extends BaseJpaSpecificationQuery<Dataset> {

    private String keyword;

}
