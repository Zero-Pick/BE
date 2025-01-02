package kw.zeropick.review.service;

import java.util.List;
import kw.zeropick.review.domain.PositiveTagEnum;
import kw.zeropick.review.domain.Review;
import kw.zeropick.review.dto.request.ReviewRequestDto;
import kw.zeropick.review.dto.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewService {
    public Page<ReviewResponse> getReviews(Long productId, PositiveTagEnum positiveTag, String sort, Pageable pageable);
    public void createReview(ReviewRequestDto reviewRequestDto, List<MultipartFile> files);
}
