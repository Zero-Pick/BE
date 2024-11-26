package kw.zeropick.review.dto;

import jakarta.validation.constraints.NotNull;
import kw.zeropick.review.domain.NegativeTagEnum;
import kw.zeropick.review.domain.PositiveTagEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReviewRequestDto {
    @NotNull
    private Long productId; // 연결된 상품 ID
    @NotNull
    private Long rating; // 별점
    @NotNull
    private String title; // 리뷰 제목
    @NotNull
    private String content; // 리뷰 내용

    // 긍정 태그 및 부정 태그 추가
    private List<PositiveTagEnum> positiveTags;
    private List<NegativeTagEnum> negativeTags;
}
