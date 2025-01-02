package kw.zeropick.review.repository;

import kw.zeropick.review.domain.PositiveTagEnum;
import kw.zeropick.review.domain.Review;
import kw.zeropick.review.dto.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewQueryDslRepository {
    public Page<ReviewResponse> findReviewsByProductId(Long productId, PositiveTagEnum positiveTag, String sort, Pageable pageable);
}
