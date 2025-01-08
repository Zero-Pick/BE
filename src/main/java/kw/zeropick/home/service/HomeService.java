package kw.zeropick.home.service;

import kw.zeropick.home.dto.response.ProductBestResponse;

import java.util.List;

public interface HomeService {
    List<ProductBestResponse> getTopPopularityProducts(int limit, String token);
}
