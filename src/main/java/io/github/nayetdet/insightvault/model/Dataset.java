package io.github.nayetdet.insightvault.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "datasets")
public class Dataset extends BaseModel {

    @Column(name = "record_id")
    private String recordId;

    @Column(nullable = false)
    private String name;

    @Column(name = "content_type", length = 50, nullable = false)
    private String contentType;

    @Column(name = "size_in_bytes", nullable = false)
    private Long sizeInBytes;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
