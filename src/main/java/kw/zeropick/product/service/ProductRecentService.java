package kw.zeropick.product.service;

import java.util.List;
import kw.zeropick.product.dto.ProductDto;
import kw.zeropick.product.dto.ProductRecentDto;

public interface ProductRecentService {
    List<ProductRecentDto> getRecentProducts(String cookieValue) throws Exception;
    String addRecentProduct(Long productId, String cookieValue) throws Exception;
}
