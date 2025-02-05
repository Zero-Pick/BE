package kw.zeropick.product.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import kw.zeropick.product.domain.Product;
import kw.zeropick.product.dto.ProductDto;
import kw.zeropick.product.dto.ProductRecentDto;
import kw.zeropick.product.repository.ProductJpaRepository;
import kw.zeropick.product.service.ProductRecentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductRecentServiceImpl implements ProductRecentService {
    private final ProductJpaRepository productJpaRepository;
    private final ObjectMapper objectMapper;
    private final ProductService productService;

    @Override
    public List<ProductRecentDto> getRecentProducts(String cookieValue) throws Exception {
        if (cookieValue == null || cookieValue.isEmpty()) {
            return new ArrayList<>();
        }
        String decodedValue = URLDecoder.decode(cookieValue, StandardCharsets.UTF_8);
        return objectMapper.readValue(decodedValue, new TypeReference<>() {});
    }

    @Override
    public String addRecentProduct(Long productId, String cookieValue) throws Exception {
        // 현재 상품 정보 가져오기
        ProductRecentDto productDto = toProductDto(productService.productDetail(productId));

        // 기존 쿠키 값 디코딩
        List<ProductRecentDto> recentProducts = getRecentProducts(cookieValue);

        // 기존 상품 제거 후 추가
        recentProducts.removeIf(product -> product.getId().equals(productId));
        recentProducts.add(productDto);

        // 최대 3개 유지
        if (recentProducts.size() > 3) {
            recentProducts.remove(0);
        }

        // JSON 문자열로 변환 및 인코딩
        String jsonValue = objectMapper.writeValueAsString(recentProducts);
        return URLEncoder.encode(jsonValue, StandardCharsets.UTF_8);
    }

    private ProductRecentDto toProductDto(ProductDto product) {
        return ProductRecentDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .imageUrl(product.getImageUrl())
                .build();
    }
}
