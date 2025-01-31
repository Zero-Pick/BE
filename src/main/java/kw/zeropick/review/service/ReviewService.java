package kw.zeropick.review.service;

import java.util.List;
import kw.zeropick.product.dto.ProductDto;
import kw.zeropick.review.domain.PositiveTagEnum;
import kw.zeropick.review.domain.Review;
import kw.zeropick.review.dto.request.ReviewRequestDto;
import kw.zeropick.review.dto.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewService {
    Page<ReviewResponse> getReviews(Long productId, PositiveTagEnum positiveTag, String sort, Pageable pageable);
    void createReview(Long memberId, ReviewRequestDto reviewRequestDto, List<MultipartFile> files);
    void updateReview(Long reviewId, ReviewRequestDto reviewRequestDto, List<MultipartFile> files);
    void deleteReview(Long reviewId);

    public void reviewLike(Long reviewId, Long memberId);
    public void undoReviewLike(Long reviewId, Long memberId);
    public Page<ReviewResponse> bookmarkProductList(Long memberId, int page, int size);
}
