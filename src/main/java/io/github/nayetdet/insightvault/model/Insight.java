package io.github.nayetdet.insightvault.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "insights")
public class Insight extends BaseModel {

    @Column(name = "record_id")
    private String recordId;

    @Column(length = 500, nullable = false)
    private String question;

    @Lob
    @Column(nullable = false)
    private String answer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
