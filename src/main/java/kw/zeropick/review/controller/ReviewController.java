package kw.zeropick.review.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kw.zeropick.review.domain.Review;
import kw.zeropick.review.dto.ReviewRequestDto;
import kw.zeropick.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "review", description = "리뷰 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    //리뷰 작성
    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewRequestDto reviewDTO) {
        try {
            reviewService.createReview(reviewDTO, 1L);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok().body("리뷰 작성 성공");
    }

    //리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewRequestDto reviewDTO) {
        ResponseEntity<Review> reviewResponseEntity;
        try {
            reviewResponseEntity = ResponseEntity.ok(
                    reviewService.updateReview(reviewId, reviewDTO, 1L));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok().body(reviewResponseEntity);
    }

    //리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        try {
            reviewService.deleteReview(reviewId, 1L);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok().body("리뷰 삭제 성공");
    }
}
