package kw.zeropick.product.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kw.zeropick.review.domain.NegativeTagEnum;
import kw.zeropick.review.domain.PositiveTagEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ReviewTagDto {
    private Integer tagCount;

    private PositiveTagEnum positiveTagEnum;

    private NegativeTagEnum negativeTagEnum;

    private Boolean positiveNegative;
}
