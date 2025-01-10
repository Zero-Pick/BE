package kw.zeropick.home.service;

import java.util.List;
import kw.zeropick.common.LoginUser;
import kw.zeropick.home.dto.response.HomeResponse;
import kw.zeropick.home.dto.response.ProductBestResponse;
import kw.zeropick.member.domain.MemberInterest;
import kw.zeropick.member.service.MemberService;
import kw.zeropick.product.domain.Category;
import kw.zeropick.product.domain.Product;
import kw.zeropick.product.domain.ProductSort;
import kw.zeropick.product.dto.ProductDto;
import kw.zeropick.product.dto.request.ProductSearchRequest;
import kw.zeropick.product.service.ProductService;
import kw.zeropick.review.dto.response.ReviewResponse;
import kw.zeropick.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {
    private final ProductService productService;
    private final ReviewService reviewService;
    private final MemberService memberService;

    @Override
    public List<ProductBestResponse> categoryBest(Long memberId, Category category) {
        ProductSearchRequest productSearchRequest = ProductSearchRequest.builder()
                .category(category)
                .sort(ProductSort.POPULARITY)
                .build();

        Page<ProductDto> productDtos = productService.productSearch(memberId, 0, 3, productSearchRequest);
        Pageable pageable = PageRequest.of(0, 1);

        List<ProductBestResponse> bestResponses = productDtos.getContent().stream().map(productDto -> {
            Page<ReviewResponse> reviews = reviewService.getReviews(productDto.getId(), null, "latest", pageable);
            ReviewResponse latestReview = reviews.getContent().isEmpty() ? null : reviews.getContent().get(0);

            return ProductBestResponse.builder()
                    .id(productDto.getId())
                    .productName(productDto.getProductName())
                    .category(productDto.getCategory())
                    .zeroSugar(productDto.getZeroSugar())
                    .zeroKcal(productDto.getZeroKcal())
                    .price(productDto.getPrice())
                    .starRate(productDto.getStarRate())
                    .imageUrl(productDto.getImageUrl())
                    .reviewCount(productDto.getReviewCount())
                    .reviewResponse(latestReview)
                    .bookmarked(productDto.getBookmarked())
                    .compared(productDto.getCompared())
                    .build();
        }).toList();

        return bestResponses;
    }
//
//    @Override
//    @Transactional
//    public ProductBestResponse toProductBestResponse (Product product){
//        Long memberId = null;
//        try {
//            memberId = LoginUser.get().getId(); // 로그인 사용자 정보 가져오기
//        } catch (Exception e) {
//            // 비회원인 경우 memberId는 null로 설정
//        }
//
//        ProductBestResponse.ProductBestResponseBuilder builder = ProductBestResponse.builder()
//                .id(product.getId())
//                .productName(product.getProductName())
//                .category(product.getCategory())
//                .zeroSugar(product.getZeroSugar())
//                .zeroKcal(product.getZeroKcal())
//                .price(product.getPrice())
//                .starRate(product.getStarRate())
//                .imageUrl(product.getImageUrl())
//                .reviewCount(product.getReviewCount());
//
//        if (memberId != null) { // 회원일 경우
//            builder.bookmarked(productService.isBookmarkedByUser(product.getId(), memberId))
//                    .compared(productService.isComparedByUser(product.getId(), memberId));
//        } else { // 비회원일 경우
//            builder.bookmarked(null)
//                    .compared(null);
//        }
//
//        return builder.build();
//    }

    @Override
    @Transactional
    public HomeResponse getHomeResponse(String token) {
        HomeResponse.HomeResponseBuilder responseBuilder = HomeResponse.builder();

        // 공통 인기 상품 검색 요청 생성
        ProductSearchRequest popularRequest = ProductSearchRequest.builder()
                .sort(ProductSort.POPULARITY)
                .build();

        // 인기 상품 검색 (4개)
        List<ProductBestResponse> topPopularityProducts = productService.productSearch(1L, 0, 4, popularRequest)
                .getContent()
                .stream()
                .map(this::toProductBestResponse)
                .collect(Collectors.toList());

        responseBuilder.topPopularityProducts(topPopularityProducts);

        // 회원일 경우 추가적으로 추천 상품을 검색
        if (token != null) {
            MemberInterest memberInterest = memberService.getMemberInterest(1L);

            ProductSearchRequest recommendedRequest = createRecommendedRequest(memberInterest);

            List<ProductBestResponse> recommendedProducts = productService.productSearch(1L, 0, 4, recommendedRequest)
                    .getContent()
                    .stream()
                    .map(this::toProductBestResponse)
                    .collect(Collectors.toList());

            responseBuilder.recommendedProducts(recommendedProducts);
        }

        return responseBuilder.build();
    }

    private ProductBestResponse toProductBestResponse(ProductDto productDto) {
        return ProductBestResponse.builder()
                .id(productDto.getId())
                .productName(productDto.getProductName())
                .category(productDto.getCategory())
                .zeroSugar(productDto.getZeroSugar())
                .zeroKcal(productDto.getZeroKcal())
                .price(productDto.getPrice())
                .starRate(productDto.getStarRate())
                .imageUrl(productDto.getImageUrl())
                .reviewCount(productDto.getReviewCount())
                .bookmarked(productDto.getBookmarked())
                .compared(productDto.getCompared())
                .build();
    }

    private ProductSearchRequest createRecommendedRequest(MemberInterest memberInterest) {
        ProductSearchRequest.ProductSearchRequestBuilder requestBuilder = ProductSearchRequest.builder()
                .sort(ProductSort.POPULARITY);

        if (memberInterest == MemberInterest.BOTH) {
            requestBuilder.zeroSugar(true).zeroKcal(true);
        } else if (memberInterest == MemberInterest.ZEROKCAL) {
            requestBuilder.zeroSugar(false).zeroKcal(true);
        } else if (memberInterest == MemberInterest.ZEROSUGAR) {
            requestBuilder.zeroSugar(true).zeroKcal(false);
        } else if (memberInterest == MemberInterest.NONE) {
            requestBuilder.zeroSugar(false).zeroKcal(false);
        }

        return requestBuilder.build();
    }

}