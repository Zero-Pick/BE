package kw.zeropick.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import kw.zeropick.payload.ApiResponse;
import kw.zeropick.product.dto.ProductDto;
import kw.zeropick.review.domain.PositiveTagEnum;
import kw.zeropick.review.domain.Review;
import kw.zeropick.review.dto.request.ReviewRequestDto;
import kw.zeropick.review.dto.response.ReviewResponse;
import kw.zeropick.review.service.ReviewService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "review", description = "리뷰 관련 API")
@Builder
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "리뷰 등록", description = "새로운 리뷰를 등록합니다.")
    @PostMapping()
    public ResponseEntity<String> createReview(
            @RequestPart(value = "review") ReviewRequestDto reviewRequestDto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        reviewService.createReview(reviewRequestDto, files);
        return ResponseEntity.ok("리뷰가 성공적으로 등록되었습니다.");
    }

    @Operation(summary = "리뷰 수정", description = "리뷰를 수정합니다.")
    @PatchMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(
            @PathVariable Long reviewId,
            @RequestPart(value = "review") ReviewRequestDto reviewRequestDto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        reviewService.updateReview(reviewId, reviewRequestDto, files);
        return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다.");
    }

    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제합니다.")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
    }

    @Operation(summary = "리뷰 조회", description = "상품에 대한 리뷰를 조회합니다.")
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getReviews(
            @PathVariable Long productId,
            @RequestParam(required = false) PositiveTagEnum positiveTag,
            @RequestParam(required = false) String sort,
            Pageable pageable
    ) {
        try {
            Page<ReviewResponse> reviews = reviewService.getReviews(productId, positiveTag, sort, pageable);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .check(true)
                            .information(reviews)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    ApiResponse.builder()
                            .check(false)
                            .information(e.getMessage())
                            .build()
            );
        }
    }
}
