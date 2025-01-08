package kw.zeropick.product.dto.request;

import java.util.List;
import kw.zeropick.product.domain.Category;
import kw.zeropick.review.domain.PositiveTagEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductSearchRequest {
    private String keyword;
    private Boolean zeroSugar;
    private Boolean zeroKcal;
    private Boolean exceptErythritol;
    private Boolean exceptAllulose;
    private List<PositiveTagEnum> tags; // 태그 검색 조건
    private Category category;          // 카테고리 검색 조건 추가
}