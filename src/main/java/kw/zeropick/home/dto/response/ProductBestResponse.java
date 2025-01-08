package kw.zeropick.home.dto.response;

import kw.zeropick.product.domain.Category;
import kw.zeropick.review.dto.response.ReviewResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Builder
@Data
@AllArgsConstructor
public class ProductBestResponse {
    private Long id;

    private String productName;

    private Category category;

    private Boolean zeroSugar;

    private Boolean zeroKcal;

    private int price;

    private Double starRate;

    private String imageUrl;

    private int reviewCount;

    private ReviewResponse reviewResponse;

    //  좋아요 여부
    @Setter
    private Boolean bookmarked;
    //  비교함 여부
    @Setter
    private Boolean compared;
}
