package kw.zeropick.home.dto.response;

import kw.zeropick.product.dto.ProductDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class HomeResponse {
    private List<ProductBestResponse> topPopularityProducts; // 인기 상품 리스트
    private List<ProductBestResponse> recommendedProducts; // 추천 상품 리스트
}
