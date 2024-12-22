package kw.zeropick.product.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private int bookmarkCount;

    private int reviewCount;

    private IngredientDto ingredient;

//  좋아요 여부
    @Setter
    private Boolean bookmarked;
//  비교함 여부
    @Setter
    private Boolean compared;
}
