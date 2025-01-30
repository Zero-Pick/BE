package kw.zeropick.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import kw.zeropick.common.LoginUser;
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
        Long memberId = 1L;
        reviewService.createReview(memberId, reviewRequestDto, files);
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
    // 리뷰 좋아요 하기
    @Operation(summary = "리뷰 좋아요 하기", description = "리뷰 좋아요 추가 요청(로그인 기능 적용 전이므로 1번유저 고정)")
    @PostMapping("/likeReview/{reviewId}")
    public ResponseEntity<ApiResponse> productCompare(@PathVariable Long reviewId) {
        Long memberId = LoginUser.get().getId();
        try {
            reviewService.reviewLike(reviewId, memberId);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .check(true)
                            .information("Review like successfully")
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

    // 리뷰 좋아요 취소
    @Operation(summary = "리뷰 좋아요 취소", description = "리뷰 좋아요 취소 요청(로그인 기능 적용 전이므로 1번유저 고정)")
    @DeleteMapping("/likeReview/{reviewId}")
    public ResponseEntity<ApiResponse> productCompareUndo(@PathVariable Long reviewId) {
        Long memberId = LoginUser.get().getId();
        try {
            reviewService.undoReviewLike(reviewId, memberId);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .check(true)
                            .information("Review like deleted successfully")
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
