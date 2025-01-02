package kw.zeropick.product.dto.request;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductSearchRequest {
    private String keyword; // 상품명 또는 브랜드명
    private Boolean zeroSugar;
    private Boolean zeroKcal;
    private Boolean exceptAllulose; // true면 없는 것만 검색
    private Boolean exceptErythritol; // true면 없는 것만 검색
//    private List<?> reviewTags; // 리뷰 태그
}
