package kw.zeropick.home.service;

import java.util.List;
import kw.zeropick.home.dto.response.ProductBestResponse;
import kw.zeropick.product.domain.Category;

public interface HomeService {
    List<ProductBestResponse> categoryBest(Long memberId, Category category);
}
