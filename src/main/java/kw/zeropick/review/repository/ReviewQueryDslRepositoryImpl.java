package kw.zeropick.review.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kw.zeropick.review.domain.*;
import kw.zeropick.review.dto.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReviewQueryDslRepositoryImpl implements ReviewQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReviewResponse> findReviewsByProductId(Long productId, PositiveTagEnum positiveTag, String sort, Pageable pageable) {
        QReview review = QReview.review;
        QReviewTagMapping reviewTagMapping = QReviewTagMapping.reviewTagMapping;
        QReviewTag reviewTag = QReviewTag.reviewTag;

        BooleanBuilder builder = new BooleanBuilder();

        // 상품 ID 조건
        builder.and(review.product.id.eq(productId));

        // 태그 필터링 조건
        if (positiveTag != null) {
            builder.and(reviewTagMapping.reviewTag.positiveTagEnum.eq(positiveTag));
        }

        // 기본 쿼리 작성
        var query = queryFactory
                .select(review) // REVIEW만 SELECT
                .from(review)
                .leftJoin(review.tagMappings, reviewTagMapping)
                .leftJoin(reviewTagMapping.reviewTag, reviewTag)
                .where(builder);

        // 정렬 조건 추가
        if ("highRating".equalsIgnoreCase(sort)) {
            query.orderBy(review.rating.desc());
        } else if ("lowRating".equalsIgnoreCase(sort)) {
            query.orderBy(review.rating.asc());
        } else {
            query.orderBy(review.createdAt.desc());
        }

        // 페이징 처리
        List<Review> reviews = query
                .distinct() // 중복 제거
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

//        // 전체 데이터 개수
//        long total = queryFactory
//                .select(review.count())
//                .from(review)
//                .where(builder)
//                .fetchOne();

        // 리뷰 데이터를 ReviewResponse로 매핑
        List<ReviewResponse> responseList = reviews.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        long total = responseList.size();
        return new PageImpl<>(responseList, pageable, total);
    }

    private ReviewResponse mapToResponse(Review review) {
        List<PositiveTagEnum> positiveTags = review.getTags().stream()
                .filter(tag -> tag.getPositiveTagEnum() != null)
                .map(ReviewTag::getPositiveTagEnum)
                .collect(Collectors.toList());

        List<NegativeTagEnum> negativeTags = review.getTags().stream()
                .filter(tag -> tag.getNegativeTagEnum() != null)
                .map(ReviewTag::getNegativeTagEnum)
                .collect(Collectors.toList());

        return ReviewResponse.builder()
                .id(review.getId())
                .userName("User") // 실제 사용자 정보를 가져오는 로직으로 대체
                .rating(review.getRating())
                .content(review.getContent())
                .likeCount(review.getLikeCount())
                .imageUrls(review.getImageUrls())
                .positiveTags(positiveTags)
                .negativeTags(negativeTags)
                .build();
    }

}
