package kw.zeropick.product.repository;

import kw.zeropick.product.domain.Product;
import kw.zeropick.product.dto.request.ProductSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQueryDslRepository {
    Page<Product> searchProducts(ProductSearchRequest request, Pageable pageable);
}
