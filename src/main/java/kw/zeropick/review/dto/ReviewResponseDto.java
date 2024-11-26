package kw.zeropick.review.dto;

import kw.zeropick.review.domain.NegativeTagEnum;
import kw.zeropick.review.domain.PositiveTagEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReviewResponseDto {
    private Long id;
    private Long productId;
    private Long userId;
    private Long rating;
    private String title;
    private String content;

    private List<PositiveTagEnum> positiveTags; // 긍정 태그
    private List<NegativeTagEnum> negativeTags; // 부정 태그
}
