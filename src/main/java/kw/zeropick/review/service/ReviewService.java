package kw.zeropick.review.service;

import kw.zeropick.review.domain.PositiveTagEnum;
import kw.zeropick.review.domain.Review;
import kw.zeropick.review.dto.request.ReviewRequestDto;
import kw.zeropick.review.dto.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    public Page<ReviewResponse> getReviews(Long productId, PositiveTagEnum positiveTag, String sort, Pageable pageable);
    public void createReview(ReviewRequestDto reviewRequestDto);
}
