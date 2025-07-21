package io.github.nayetdet.insightvault.payload.query.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationPageable {

    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;

}
