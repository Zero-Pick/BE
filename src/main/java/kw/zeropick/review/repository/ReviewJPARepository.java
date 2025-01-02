package kw.zeropick.review.repository;

import kw.zeropick.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJPARepository extends JpaRepository<Review, Long> {
}
