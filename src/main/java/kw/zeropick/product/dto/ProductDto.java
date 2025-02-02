package kw.zeropick.product.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;
import kw.zeropick.product.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;

    private String productName;

    private String brand;

    private Category category;

    private Boolean zeroSugar;

    private Boolean zeroKcal;

    private int price;

    private Double starRate;

    private int viewCount;

    private String imageUrl;

    private String productLink; // 최저가 상품 링크 추가

    private int bookmarkCount;

    private int reviewCount;

    private IngredientDto ingredient;

//  감미료 정보
    @Setter
    private List<ArtificialSweetDto> artificialSweets = new ArrayList<>();

//  해당상품 태그들
    @Setter
    private List<ReviewTagDto> reviewTags = new ArrayList<>();

//  별점 통계
    @Setter
    private ReviewInfoDto reviewInfo;

//  좋아요 여부
    @Setter
    private Boolean bookmarked;
//  비교함 여부
    @Setter
    private Boolean compared;
}
