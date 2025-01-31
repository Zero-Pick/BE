package kw.zeropick.product.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import kw.zeropick.common.converter.StringListToStringConverter;
import kw.zeropick.common.domain.BaseEntity;
import kw.zeropick.review.domain.NegativeTagEnum;
import kw.zeropick.review.domain.PositiveTagEnum;
import kw.zeropick.review.domain.Review;
import lombok.*;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String productName;

    private String brand;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Boolean zeroSugar;

    private Boolean zeroKcal;

    private Boolean bloodSugar;

    private int price;

    private Double starRate;

    private int viewCount;

    private String imageUrl;

    private int bookmarkCount;

    private int reviewCount;

    //상품 별 긍정태그 상위 3개
    @Convert(converter = StringListToStringConverter.class)
    private List<PositiveTagEnum> positiveTop3tags;

    //상품 별 부정태그 상위 3개
    @Convert(converter = StringListToStringConverter.class)
    private List<NegativeTagEnum> negativeTop3tags;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(mappedBy = "product")
    private Ingredient ingredient;

    //식품 대분류
    private String bigCategory;

    //대표 식품명
    private String foodName;

    // 인기순
    private int popularity;

    public void incrementBookmarkCount() {
        this.bookmarkCount++;
        this.setPopularity();
    }

    public void decrementBookmarkCount() {
        this.bookmarkCount--;
        this.setPopularity();
    }

    public void incrementViewCount() {
        this.viewCount++;
        this.setPopularity();
    }

    public void decrementViewCount() {
        this.viewCount--;
    }

    public void setStarRate(Double starRate) {
        this.starRate = Math.round(starRate * 100) / 100.0;
        this.setPopularity();
    }
    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
        this.setPopularity();
    }
    public void setPositiveTop3tags(List<PositiveTagEnum> positiveTop3tags) {
        this.positiveTop3tags = positiveTop3tags;
    }

    public void setNegativeTop3tags(List<NegativeTagEnum> negativeTop3tags) {
        this.negativeTop3tags = negativeTop3tags;
    }

    public void setPopularity() {
        int starRateScore = this.starRate != null ? (int) Math.round(this.starRate * 20 * 350) : 0;
        this.popularity = this.viewCount * 150 + this.bookmarkCount * 250 + this.reviewCount * 250 + starRateScore;
    }



}


//ALTER TABLE product
//ADD COLUMN star_rate DOUBLE DEFAULT 0.0 NOT NULL;
//
//ALTER TABLE product
//ADD COLUMN view_count INT DEFAULT 0 NOT NULL;
//
//ALTER TABLE product
//ADD COLUMN bookmark_count INT DEFAULT 0 NOT NULL;
//
//ALTER TABLE product
//ADD COLUMN review_count INT DEFAULT 0 NOT NULL;
//
//ALTER TABLE product
//ADD COLUMN image_url VARCHAR(255) DEFAULT NULL;
