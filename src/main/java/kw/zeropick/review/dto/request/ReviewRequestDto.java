package kw.zeropick.review.dto.request;

import kw.zeropick.review.domain.NegativeTagEnum;
import kw.zeropick.review.domain.PositiveTagEnum;
import lombok.Data;

import java.util.List;

@Data
public class ReviewRequestDto {
    private Long productId;
    private Long rating;
    private String content;
    private Boolean purchaseAgain;
    private List<String> imageUrls;
    private List<PositiveTagEnum> positiveTags;
    private List<NegativeTagEnum> negativeTags;
}