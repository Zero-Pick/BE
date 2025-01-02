package kw.zeropick.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kw.zeropick.common.domain.BaseEntity;
import kw.zeropick.member.domain.Member;
import kw.zeropick.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewTagMapping extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapping_id", updatable = false, nullable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewTag_id", nullable = false)
    private ReviewTag reviewTag;

    public ReviewTagMapping(Review review, ReviewTag reviewTag) {
        this.review = review;
        this.reviewTag = reviewTag;
    }
}


