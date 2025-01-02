package kw.zeropick.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewTag_id")
    private Long id;

    private Integer tagCount;

    @Enumerated(EnumType.STRING)
    private PositiveTagEnum positiveTagEnum;

    @Enumerated(EnumType.STRING)
    private NegativeTagEnum negativeTagEnum;

    private Boolean positiveNegative;

    @Column(nullable = false)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    public void setTagCount(Integer tagCount) {
        this.tagCount = tagCount;
    }

    public void setReview(Review review) {this.review = review;}
}

