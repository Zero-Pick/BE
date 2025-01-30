package kw.zeropick.review.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import kw.zeropick.common.domain.exception.ResourceNotFoundException;
import kw.zeropick.member.domain.Member;
import kw.zeropick.member.repository.MemberJpaRepository;
import kw.zeropick.product.domain.Compare;
import kw.zeropick.product.domain.Product;
import kw.zeropick.product.repository.ProductJpaRepository;
import kw.zeropick.review.domain.*;
import kw.zeropick.review.dto.request.ReviewRequestDto;
import kw.zeropick.review.dto.response.ReviewResponse;
import kw.zeropick.review.repository.ReviewJpaRepository;
import kw.zeropick.review.repository.ReviewLikeJpaRepository;
import kw.zeropick.review.repository.ReviewTagJpaRepository;
import kw.zeropick.review.repository.ReviewTagMappingJpaRepository;
import kw.zeropick.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final MemberJpaRepository memberJpaRepository;
    private final ReviewJpaRepository reviewRepository;
    private final ProductJpaRepository productRepository;
    private final ReviewTagJpaRepository reviewTagRepository;
    private final ReviewTagMappingJpaRepository reviewTagMappingRepository;
    private final ReviewLikeJpaRepository reviewLikeJpaRepository;

    private final S3Util s3Util;

    public Page<ReviewResponse> getReviews(Long productId, PositiveTagEnum positiveTag, String sort, Pageable pageable) {
        Long currentMemberId = 1L; // 로그인 적용 전으로 1L 고정
        Member currentMember = memberJpaRepository.findById(currentMemberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버를 찾을 수 없습니다."));

        Page<ReviewResponse> reviewsByProductId = reviewRepository.findReviewsByProductId(productId, positiveTag, sort, pageable);

        // myReview 값 설정
        return reviewsByProductId.map(reviewResponse -> {
            boolean isMyReview = reviewResponse.getUserName() != null && currentMember.getName().equals(reviewResponse.getUserName());
            reviewResponse.setMyReview(isMyReview);
            return reviewResponse;
        });
    }

    @Override
    @Transactional
    public void createReview(Long memberId, ReviewRequestDto reviewRequestDto, List<MultipartFile> files) {
        Product product = productRepository.findById(reviewRequestDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("해당 상품을 찾을 수 없습니다."));

        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 회원을 찾을 수 없습니다."));

        double newStarRate = ((product.getStarRate() * product.getReviewCount()) + reviewRequestDto.getRating()) / (product.getReviewCount() + 1);
        product.setStarRate(newStarRate);
        product.setReviewCount(product.getReviewCount() + 1);
        productRepository.save(product);

        List<String> images = new ArrayList<>();

        // 이미지 파일 처리 및 S3 업로드
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    String imageUrl = s3Util.upload(file);
                    System.out.println(imageUrl);
                    images.add(imageUrl);
                }
            }
        }

        Review review = Review.builder()
                .product(product)
                .member(member)
                .rating(reviewRequestDto.getRating())
                .content(reviewRequestDto.getContent())
                .imageUrls(images)
                .build();
        reviewRepository.save(review);

        saveReviewTags(reviewRequestDto, review, product);
    }

    @Override
    @Transactional
    public void updateReview(Long reviewId, ReviewRequestDto reviewRequestDto, List<MultipartFile> files) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("해당 리뷰를 찾을 수 없습니다."));

        Product product = existingReview.getProduct();
        double newStarRate = ((product.getStarRate() * product.getReviewCount()) - existingReview.getRating() + reviewRequestDto.getRating()) / product.getReviewCount();
        product.setStarRate(newStarRate);
        productRepository.save(product);

        existingReview.setRating(reviewRequestDto.getRating());
        existingReview.setContent(reviewRequestDto.getContent());
        List<String> oldImageUrls = existingReview.getImageUrls();
        if(!oldImageUrls.isEmpty()) {
            for (String imageUrl : oldImageUrls) {
                s3Util.deleteFile(imageUrl);
            }
        }
        List<String> newImageUrls = new ArrayList<>();
        // 이미지 파일 처리 및 S3 업로드
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    String imageUrl = s3Util.upload(file);
                    System.out.println(imageUrl);
                    newImageUrls.add(imageUrl);
                }
            }
        }
        existingReview.setImageUrls(newImageUrls);
        reviewRepository.save(existingReview);

        reviewTagMappingRepository.deleteAllByReview(existingReview);
        saveReviewTags(reviewRequestDto, existingReview, product);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("해당 리뷰를 찾을 수 없습니다."));
        Product product = review.getProduct();

        double newStarRate = ((product.getStarRate() * product.getReviewCount()) - review.getRating()) / (product.getReviewCount() - 1);
        product.setStarRate(product.getReviewCount() > 1 ? newStarRate : 0.0);
        product.setReviewCount(product.getReviewCount() - 1);
        productRepository.save(product);

        reviewTagMappingRepository.deleteAllByReview(review);
        reviewRepository.delete(review);

        updateTopTags(product);
    }

    private void saveReviewTags(ReviewRequestDto reviewRequestDto, Review review, Product product) {
        reviewRequestDto.getPositiveTags().forEach(tag -> saveTag(product, review, tag, true));
        reviewRequestDto.getNegativeTags().forEach(tag -> saveTag(product, review, tag, false));
        updateTopTags(product);
    }

    private void saveTag(Product product, Review review, Enum<?> tag, boolean isPositive) {
        Optional<ReviewTag> existingTag = reviewTagRepository.findByProductIdAndTag(
                product.getId(), isPositive ? (PositiveTagEnum) tag : null, isPositive ? null : (NegativeTagEnum) tag);
        ReviewTag reviewTag = existingTag.orElseGet(() -> ReviewTag.builder()
                .productId(product.getId())
                .positiveTagEnum(isPositive ? (PositiveTagEnum) tag : null)
                .negativeTagEnum(isPositive ? null : (NegativeTagEnum) tag)
                .tagCount(0)
                .positiveNegative(isPositive)
                .build());
        reviewTag.setTagCount(reviewTag.getTagCount() + 1);
        reviewTagRepository.save(reviewTag);
        reviewTagMappingRepository.save(new ReviewTagMapping(review, reviewTag));
    }

    private void updateTopTags(Product product) {
        List<PositiveTagEnum> topPositiveTags = reviewTagRepository.findAll().stream()
                .filter(tag -> tag.getPositiveNegative() && tag.getProductId().equals(product.getId()))
                .sorted(Comparator.comparingInt(ReviewTag::getTagCount).reversed())
                .limit(3)
                .map(ReviewTag::getPositiveTagEnum)
                .collect(Collectors.toList());
        product.setPositiveTop3tags(topPositiveTags);

        List<NegativeTagEnum> topNegativeTags = reviewTagRepository.findAll().stream()
                .filter(tag -> !tag.getPositiveNegative() && tag.getProductId().equals(product.getId()))
                .sorted(Comparator.comparingInt(ReviewTag::getTagCount).reversed())
                .limit(3)
                .map(ReviewTag::getNegativeTagEnum)
                .collect(Collectors.toList());
        product.setNegativeTop3tags(topNegativeTags);

        productRepository.save(product);
    }

    @Override
    public Page<ReviewResponse> bookmarkProductList(Long memberId, int page, int size) {
//        좋아요한 리뷰 보류
        return null;
    }

    @Override
    @Transactional
    public void reviewLike(Long reviewId, Long memberId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 리뷰 없음 id: " + reviewId));

        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 유저 없음 id: " + memberId));

        if(reviewLikeJpaRepository.existsByReviewAndMember(review, member)) {
            throw new RuntimeException("이미 좋아요한 리뷰입니다.");
        }

        review.setLikeCount(review.getLikeCount() + 1);
        reviewRepository.save(review);

        ReviewLike reviewLike = new ReviewLike(member, review);
        reviewLikeJpaRepository.save(reviewLike);
    }

    @Override
    @Transactional
    public void undoReviewLike(Long reviewId, Long memberId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 리뷰 없음 id: " + reviewId));

        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 유저 없음 id: " + memberId));

        ReviewLike reviewLike = reviewLikeJpaRepository.findByReviewAndMember(review, member)
                .orElseThrow(() -> new EntityNotFoundException("해당 리뷰는 좋아요 목록에 없습니다."));

        review.setLikeCount(review.getLikeCount() - 1);
        reviewRepository.save(review);

        reviewLikeJpaRepository.delete(reviewLike);
    }
}
