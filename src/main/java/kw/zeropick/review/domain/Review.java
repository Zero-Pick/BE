package kw.zeropick.review.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import kw.zeropick.common.converter.StringListToStringConverter;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    private Long rating;

    @NotNull
    private String content;

    @Convert(converter = StringListToStringConverter.class)
    private List<String> imageUrls = new ArrayList<>();

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewTagMapping> tagMappings = new ArrayList<>();

    public List<ReviewTag> getTags() {
        List<ReviewTag> tags = new ArrayList<>();
        for (ReviewTagMapping mapping : tagMappings) {
            tags.add(mapping.getReviewTag());
        }
        return tags;
    }
}
