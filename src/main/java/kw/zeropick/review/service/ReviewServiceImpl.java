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
            reviewTag.setReview(review);
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
            reviewTag.setReview(review);
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
}
