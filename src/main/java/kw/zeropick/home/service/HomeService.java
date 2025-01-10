package kw.zeropick.home.service;

import java.util.List;

import kw.zeropick.home.dto.response.HomeResponse;
import kw.zeropick.home.dto.response.ProductBestResponse;
import kw.zeropick.product.domain.Category;
import kw.zeropick.product.domain.Product;

public interface HomeService {
    List<ProductBestResponse> categoryBest(Long memberId, Category category);
    HomeResponse getHomeResponse(String token);
}
