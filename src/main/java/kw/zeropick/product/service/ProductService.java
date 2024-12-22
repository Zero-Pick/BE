package kw.zeropick.product.service;

import java.util.List;
import kw.zeropick.product.dto.ProductDto;

public interface ProductService {
    public void bookmark(Long productId, Long memberId);
    public void undoBookmark(Long productId, Long memberId);
    public void compare(Long productId, Long memberId);
    public void undoCompare(Long productId, Long memberId);

    public List<ProductDto> bookmarkProductList(Long memberId);
    public List<ProductDto> compareProductList(Long memberId);

    public ProductDto productDetail(Long productId);
}
