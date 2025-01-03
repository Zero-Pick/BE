package kw.zeropick.review.repository;

import kw.zeropick.review.domain.Review;
import kw.zeropick.review.domain.ReviewTagMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewTagMappingJpaRepository extends JpaRepository<ReviewTagMapping, Long> {
    void deleteAllByReview(Review review);
    List<ReviewTagMapping> findAllByReview(Review review);
}
