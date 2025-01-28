package kw.zeropick.review.repository;

import java.util.List;
import java.util.Optional;
import kw.zeropick.member.domain.Member;
import kw.zeropick.review.domain.Review;
import kw.zeropick.review.domain.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeJpaRepository extends JpaRepository<ReviewLike, Long> {
    Boolean existsByReviewAndMember(Review review, Member member);
    Optional<ReviewLike> findByReviewAndMember(Review review, Member member);
    List<ReviewLike> findAllByMemberId(Long memberId);
    Boolean existsByReviewIdAndMemberId(Long reviewId, Long memberId);
    Long countByReview(Review review);
}
