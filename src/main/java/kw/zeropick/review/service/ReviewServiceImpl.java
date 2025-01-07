package kw.zeropick.review.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kw.zeropick.product.domain.Product;
import kw.zeropick.product.repository.ProductJpaRepository;
import kw.zeropick.review.domain.PositiveTagEnum;
import kw.zeropick.review.domain.Review;
import kw.zeropick.review.domain.ReviewTag;
import kw.zeropick.review.domain.ReviewTagMapping;
import kw.zeropick.review.dto.request.ReviewRequestDto;
import kw.zeropick.review.dto.response.ReviewResponse;
import kw.zeropick.review.repository.ReviewJpaRepository;
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
    private final ReviewJpaRepository reviewRepository;
    private final ProductJpaRepository productRepository;
    private final ReviewTagJpaRepository reviewTagRepository;
    private final ReviewTagMappingJpaRepository reviewTagMappingRepository;

    private final S3Util s3Util;

    public Page<ReviewResponse> getReviews(Long productId, PositiveTagEnum positiveTag, String sort, Pageable pageable) {
        return reviewRepository.findReviewsByProductId(productId, positiveTag, sort, pageable);
    }

    @Override
    @Transactional
    public void createReview(ReviewRequestDto reviewRequestDto, List<MultipartFile> files) {
        Product product = productRepository.findById(reviewRequestDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("해당 상품을 찾을 수 없습니다."));

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
                .rating(reviewRequestDto.getRating())
                .content(reviewRequestDto.getContent())
                .imageUrls(images)
                .build();
        reviewRepository.save(review);

        reviewRequestDto.getPositiveTags().forEach(tag -> {
            Optional<ReviewTag> existingTag = reviewTagRepository.findByProductIdAndTag(product.getId(), tag, null);
            ReviewTag reviewTag;
            if (existingTag.isPresent()) {
                reviewTag = existingTag.get();
                reviewTag.setTagCount(reviewTag.getTagCount() + 1);
            } else {
                reviewTag = ReviewTag.builder()
                        .productId(product.getId())
                        .positiveTagEnum(tag)
                        .tagCount(1)
                        .positiveNegative(true)
                        .build();
            }
//            reviewTag.setReview(review);
            reviewTagRepository.save(reviewTag);
            reviewTagMappingRepository.save(new ReviewTagMapping(review, reviewTag));
        });

        reviewRequestDto.getNegativeTags().forEach(tag -> {
            Optional<ReviewTag> existingTag = reviewTagRepository.findByProductIdAndTag(product.getId(), null, tag);
            ReviewTag reviewTag;
            if (existingTag.isPresent()) {
                reviewTag = existingTag.get();
                reviewTag.setTagCount(reviewTag.getTagCount() + 1);
            } else {
                reviewTag = ReviewTag.builder()
                        .productId(product.getId())
                        .negativeTagEnum(tag)
                        .tagCount(1)
                        .positiveNegative(false)
                        .build();
            }
//            reviewTag.setReview(review);
            reviewTagRepository.save(reviewTag);
            reviewTagMappingRepository.save(new ReviewTagMapping(review, reviewTag));
        });

        List<PositiveTagEnum> topTags = reviewTagRepository.findAll().stream()
                .filter(tag -> tag.getPositiveNegative() && tag.getProductId() == product.getId())
                .sorted((t1, t2) -> t2.getTagCount().compareTo(t1.getTagCount()))
                .limit(3)
                .map(ReviewTag::getPositiveTagEnum)
                .collect(Collectors.toList());

        product.setTags(topTags);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateReview(Long reviewId, ReviewRequestDto reviewRequestDto) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("해당 리뷰를 찾을 수 없습니다."));

        Product product = existingReview.getProduct();

        // 기존 평점 제거 후 새로운 평점 반영
        double newStarRate = ((product.getStarRate() * product.getReviewCount()) - existingReview.getRating() + reviewRequestDto.getRating()) / product.getReviewCount();
        product.setStarRate(newStarRate);
        productRepository.save(product);

        // 리뷰 정보 업데이트
        existingReview.setRating(reviewRequestDto.getRating());
        existingReview.setContent(reviewRequestDto.getContent());
        existingReview.setImageUrls(reviewRequestDto.getImageUrls());
        reviewRepository.save(existingReview);

        // 기존 태그 매핑 삭제
        List<ReviewTagMapping> existingMappings = reviewTagMappingRepository.findAllByReview(existingReview);

        reviewTagMappingRepository.deleteAllByReview(existingReview);

        for (ReviewTagMapping mapping : existingMappings) {
            ReviewTag reviewTag = mapping.getReviewTag();
            reviewTag.setTagCount(reviewTag.getTagCount() - 1); // 기존 태그 카운트 감소
            if (reviewTag.getTagCount() <= 0) {
                reviewTagRepository.delete(reviewTag); // 태그 카운트가 0이 되면 삭제
            } else {
                reviewTagRepository.save(reviewTag);
            }
        }

        // 새로운 태그 추가 및 매핑 저장
        reviewRequestDto.getPositiveTags().forEach(tag -> {
            Optional<ReviewTag> existingTag = reviewTagRepository.findByProductIdAndTag(product.getId(), tag, null);
            ReviewTag reviewTag;
            if (existingTag.isPresent()) {
                reviewTag = existingTag.get();
                reviewTag.setTagCount(reviewTag.getTagCount() + 1); // 기존 태그 카운트 증가
            } else {
                reviewTag = ReviewTag.builder()
                        .productId(product.getId())
                        .positiveTagEnum(tag)
                        .tagCount(1)
                        .positiveNegative(true)
                        .build();
            }
            reviewTagRepository.save(reviewTag);
            reviewTagMappingRepository.save(new ReviewTagMapping(existingReview, reviewTag));
        });

        reviewRequestDto.getNegativeTags().forEach(tag -> {
            Optional<ReviewTag> existingTag = reviewTagRepository.findByProductIdAndTag(product.getId(), null, tag);
            ReviewTag reviewTag;
            if (existingTag.isPresent()) {
                reviewTag = existingTag.get();
                reviewTag.setTagCount(reviewTag.getTagCount() + 1); // 기존 태그 카운트 증가
            } else {
                reviewTag = ReviewTag.builder()
                        .productId(product.getId())
                        .negativeTagEnum(tag)
                        .tagCount(1)
                        .positiveNegative(false)
                        .build();
            }
            reviewTagRepository.save(reviewTag);
            reviewTagMappingRepository.save(new ReviewTagMapping(existingReview, reviewTag));
        });

        // 새로운 topTags 계산
        List<PositiveTagEnum> topTags = reviewTagRepository.findAll().stream()
                .filter(tag -> tag.getPositiveNegative() && tag.getProductId() == product.getId())
                .sorted((t1, t2) -> t2.getTagCount().compareTo(t1.getTagCount()))
                .limit(3)
                .map(ReviewTag::getPositiveTagEnum)
                .collect(Collectors.toList());

        product.setTags(topTags);
        productRepository.save(product);
    }


}
