package kw.zeropick.review.domain;

import jakarta.persistence.*;
import kw.zeropick.common.domain.BaseEntity;
import kw.zeropick.member.domain.Member;
import kw.zeropick.product.domain.Product;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public ReviewLike(Member member, Review review) {
        this.member = member;
        this.review = review;
    }
}
