package kw.zeropick.review.repository;

import java.util.List;
import java.util.Optional;
import kw.zeropick.review.domain.NegativeTagEnum;
import kw.zeropick.review.domain.PositiveTagEnum;
import kw.zeropick.review.domain.ReviewTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewTagJpaRepository extends JpaRepository<ReviewTag, Long> {

    @Query("SELECT rt FROM ReviewTag rt WHERE rt.productId = :productId AND ((:positiveTagEnum IS NOT NULL AND rt.positiveTagEnum = :positiveTagEnum) OR (:negativeTagEnum IS NOT NULL AND rt.negativeTagEnum = :negativeTagEnum))")
    Optional<ReviewTag> findByProductIdAndTag(@Param("productId") Long productId, @Param("positiveTagEnum") PositiveTagEnum positiveTagEnum, @Param("negativeTagEnum") NegativeTagEnum negativeTagEnum);
    List<ReviewTag> findAllByProductId(Long productId);
}
