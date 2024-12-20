package kw.zeropick.product.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import kw.zeropick.review.domain.NegativeTag;
import kw.zeropick.review.domain.Review;
import lombok.*;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String productName;

    private String brand;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Boolean zeroSugar;

    private Boolean zeroKcal;

    private int price;

    private Double starRate;

    private int viewCount;

    private String imageUrl;

    private int bookmarkCount;

    private int reviewCount;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(mappedBy = "product")
    private Ingredient ingredient;

    //식품 대분류
    private String bigCategory;

    //대표 식품명
    private String foodName;
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
