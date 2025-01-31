package kw.zeropick.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ReviewInfoDto {
    private int oneStar;

    private int twoStar;

    private int threeStar;

    private int fourStar;

    private int fiveStar;
}
