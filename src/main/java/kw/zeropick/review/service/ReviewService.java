package kw.zeropick.review.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import kw.zeropick.member.domain.Member;
import kw.zeropick.member.repository.MemberJpaRepository;
import kw.zeropick.product.domain.Product;
import kw.zeropick.product.repository.ProductRepository;
import kw.zeropick.review.domain.*;
import kw.zeropick.review.dto.ReviewRequestDto;
import kw.zeropick.review.dto.ReviewResponseDto;
import kw.zeropick.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final MemberJpaRepository memberJpaRepository;

    @Transactional
    public Review createReview(ReviewRequestDto reviewDTO, Long memberId) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 유저 없음 id: " + memberId));

        Product product = productRepository.findById(reviewDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 상품 없음 id: " + reviewDTO.getProductId()));


        // 리뷰 생성
        Review review = Review.builder()
                .member(member)
                .product(product)
                .rating(reviewDTO.getRating())
                .title(reviewDTO.getTitle())
                .content(reviewDTO.getContent())
                .build();

        // 긍정 태그 처리
        if (reviewDTO.getPositiveTags() != null) {
            review.getPositiveTag().addAll(
                    reviewDTO.getPositiveTags().stream()
                            .map(tagEnum -> PositiveTag.builder()
                                    .review(review)
                                    .tagEnum(tagEnum)
                                    .tagTitle(tagEnum.name())
                                    .tagCount(0) // 초기 카운트 설정
                                    .build())
                            .collect(Collectors.toList())
            );
        }

        // 부정 태그 처리
        if (reviewDTO.getNegativeTags() != null) {
            review.getNegativeTag().addAll(
                    reviewDTO.getNegativeTags().stream()
                            .map(tagEnum -> NegativeTag.builder()
                                    .review(review)
                                    .tagNnum(tagEnum)
                                    .tagTitle(tagEnum.name())
                                    .tagCount(0) // 초기 카운트 설정
                                    .build())
                            .collect(Collectors.toList())
            );
        }

        return reviewRepository.save(review);
    }

    @Transactional
    public Review updateReview(Long reviewId, ReviewRequestDto reviewDTO, Long memberId) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 유저 없음 id: " + memberId));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 리뷰 없음 id: " + reviewId));

        review = Review.builder()
                .id(review.getId())
                .member(review.getMember()) // 기존 사용자
                .product(review.getProduct()) // 기존 상품
                .rating(reviewDTO.getRating())
                .title(reviewDTO.getTitle())
                .content(reviewDTO.getContent())
                .build();

        // 긍정 태그 업데이트
        if (reviewDTO.getPositiveTags() != null) {
            review.getPositiveTag().clear(); // 기존 태그 삭제
            Review finalReview = review;
            review.getPositiveTag().addAll(
                    reviewDTO.getPositiveTags().stream()
                            .map(tagEnum -> PositiveTag.builder()
                                    .review(finalReview)
                                    .tagEnum(tagEnum)
                                    .tagTitle(tagEnum.name())
                                    .tagCount(0)
                                    .build())
                            .collect(Collectors.toList())
            );
        }

        // 부정 태그 업데이트
        if (reviewDTO.getNegativeTags() != null) {
            review.getNegativeTag().clear(); // 기존 태그 삭제
            Review finalReview1 = review;
            review.getNegativeTag().addAll(
                    reviewDTO.getNegativeTags().stream()
                            .map(tagEnum -> NegativeTag.builder()
                                    .review(finalReview1)
                                    .tagNnum(tagEnum)
                                    .tagTitle(tagEnum.name())
                                    .tagCount(0)
                                    .build())
                            .collect(Collectors.toList())
            );
        }

        return reviewRepository.save(review);
    }

    // 리뷰 삭제
    @Transactional
    public void deleteReview(Long reviewId, Long memberId) throws IllegalAccessException {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 유저 없음 id: " + memberId));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 리뷰 없음 id: " + reviewId));

        if (!review.getMember().equals(member)) {
            throw new IllegalAccessException("리뷰 작성자만 삭제 가능합니다");
        }

        reviewRepository.deleteById(reviewId);
    }

    // 단일 리뷰 조회
    @Transactional(readOnly = true)
    public ReviewResponseDto getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 리뷰 없음 id: " + reviewId));

        return ReviewResponseDto.builder()
                .id(review.getId())
                .productId(review.getProduct().getId())
                .userId(review.getMember().getId())
                .rating(review.getRating())
                .title(review.getTitle())
                .content(review.getContent())
                .positiveTags(review.getPositiveTag().stream()
                        .map(tag -> tag.getTagEnum())
                        .collect(Collectors.toList()))
                .negativeTags(review.getNegativeTag().stream()
                        .map(tag -> tag.getTagNnum())
                        .collect(Collectors.toList()))
                .build();
    }

    // 상품에 대한 모든 리뷰 조회
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewsByProductId(Long productId) {
        return reviewRepository.findAll().stream()
                .filter(review -> review.getProduct().getId().equals(productId))
                .map(review -> ReviewResponseDto.builder()
                        .id(review.getId())
                        .productId(review.getProduct().getId())
                        .userId(review.getMember().getId())
                        .rating(review.getRating())
                        .title(review.getTitle())
                        .content(review.getContent())
                        .positiveTags(review.getPositiveTag().stream()
                                .map(tag -> tag.getTagEnum())
                                .collect(Collectors.toList()))
                        .negativeTags(review.getNegativeTag().stream()
                                .map(tag -> tag.getTagNnum())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
}
