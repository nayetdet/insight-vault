package io.github.nayetdet.insightvault.payload.query.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationPage<T> {

    private List<T> content;
    private ApplicationPageable pageable;

    public ApplicationPage(Page<T> page) {
        this.content = page.getContent();
        this.pageable = new ApplicationPageable(
                page.getPageable().getPageNumber(),
                page.getPageable().getPageSize(),
                page.getTotalElements()
        );
    }

    public ApplicationPage(List<T> content, Pageable pageable) {
        this.content = content;
        this.pageable = new ApplicationPageable(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                (long) content.size()
        );
    }

}
