package kw.zeropick.review.repository;

import kw.zeropick.review.domain.ReviewTagMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewTagMappingJpaRepository extends JpaRepository<ReviewTagMapping, Long> {
}
