package kw.zeropick.review.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kw.zeropick.common.domain.BaseEntity;
import kw.zeropick.review.domain.NegativeTagEnum;
import kw.zeropick.review.domain.PositiveTagEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private List<PositiveTagEnum> positiveTag;

    private List<NegativeTagEnum> negativeTag;

    @NotNull
    private Long rating;

    @NotNull
    private String title;

    @NotNull
    private String content;

}