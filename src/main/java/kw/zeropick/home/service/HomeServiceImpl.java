package kw.zeropick.home.service;

import kw.zeropick.common.LoginUser;
import kw.zeropick.home.dto.response.ProductBestResponse;
import kw.zeropick.product.domain.Product;
import kw.zeropick.product.domain.ProductSort;
import kw.zeropick.product.dto.ProductDto;
import kw.zeropick.product.dto.request.ProductSearchRequest;
import kw.zeropick.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService{

    private final ProductService productService;

    private ProductBestResponse toProductBestResponse(Product product) {
        Long memberId = null;
        try {
            memberId = LoginUser.get().getId(); // 로그인 사용자 정보 가져오기
        } catch (Exception e) {
            // 비회원인 경우 memberId는 null로 설정
        }

        ProductBestResponse.ProductBestResponseBuilder builder = ProductBestResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .category(product.getCategory())
                .zeroSugar(product.getZeroSugar())
                .zeroKcal(product.getZeroKcal())
                .price(product.getPrice())
                .starRate(product.getStarRate())
                .imageUrl(product.getImageUrl())
                .reviewCount(product.getReviewCount());

        if (memberId != null) { // 회원일 경우
            builder.bookmarked(productService.isBookmarkedByUser(product.getId(), memberId))
                    .compared(productService.isComparedByUser(product.getId(), memberId));
        } else { // 비회원일 경우
            builder.bookmarked(null)
                    .compared(null);
        }

        return builder.build();
    }

    @Override
    @Transactional
    public List<ProductBestResponse> getTopPopularityProducts(int limit, String token) {
        // 인기 상품 검색 요청 생성
        ProductSearchRequest productSearchRequest = ProductSearchRequest.builder()
                .zeroKcal(Boolean.TRUE)

                .sort(ProductSort.POPULARITY)
                .build();

        // 상품 검색 서비스 호출 (limit 개수만 가져옴)
        Page<ProductDto> productDtos = productService.productSearch(1L, 0, limit, productSearchRequest);

        // ProductDto 리스트를 ProductBestResponse로 매핑
        return productDtos.getContent().stream()
                .map(productDto -> ProductBestResponse.builder()
                        .id(productDto.getId())
                        .productName(productDto.getProductName())
                        .category(productDto.getCategory())
                        .zeroSugar(productDto.getZeroSugar())
                        .zeroKcal(productDto.getZeroKcal())
                        .price(productDto.getPrice())
                        .starRate(productDto.getStarRate())
                        .imageUrl(productDto.getImageUrl())
                        .reviewCount(productDto.getReviewCount())
                        .reviewResponse(null) // 리뷰 관련 로직 필요 시 업데이트
                        .bookmarked(productDto.getBookmarked())
                        .compared(productDto.getCompared())
                        .build()
                )
                .toList();
    }

}
