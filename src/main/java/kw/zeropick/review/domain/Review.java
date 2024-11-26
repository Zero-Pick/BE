package kw.zeropick.review.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import kw.zeropick.common.domain.BaseEntity;
import kw.zeropick.product.domain.Product;
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

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
    private List<PositiveTag> positiveTag  = new ArrayList<>();

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
    private List<NegativeTag> negativeTag  = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    private Long rating;

    @NotNull
    private String title;

    @NotNull
    private String content;
}