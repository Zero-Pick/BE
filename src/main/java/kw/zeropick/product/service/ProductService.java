package kw.zeropick.product.service;

import java.util.List;

import kw.zeropick.product.domain.Product;
import kw.zeropick.product.dto.ProductDto;
import kw.zeropick.product.dto.request.ProductSearchRequest;
import org.springframework.data.domain.Page;

public interface ProductService {
    public void bookmark(Long productId, Long memberId);
    public void undoBookmark(Long productId, Long memberId);
    public void compare(Long productId, Long memberId);
    public void undoCompare(Long productId, Long memberId);

    public Page<ProductDto> bookmarkProductList(Long memberId, int page, int size);
    public List<ProductDto> compareProductList(Long memberId);

    public ProductDto productDetail(Long productId);
    public Page<ProductDto> productSearch(Long memberId, int page, int size, ProductSearchRequest productSearchRequest);
    boolean isBookmarkedByUser(Long productId, Long memberId);
    boolean isComparedByUser(Long productId, Long memberId);
    List<Product> findTopProductsByPopularity(int limit);

    public Product getById(Long productId);
}
