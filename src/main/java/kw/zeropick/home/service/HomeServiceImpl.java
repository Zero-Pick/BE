package kw.zeropick.home.service;

import java.util.List;
import kw.zeropick.home.dto.response.ProductBestResponse;
import kw.zeropick.product.domain.Category;
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

@Service
@Transactional
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService{
    private final ProductService productService;
    private final ReviewService reviewService;

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

}
