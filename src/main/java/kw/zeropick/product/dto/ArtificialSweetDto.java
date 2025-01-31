package kw.zeropick.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ArtificialSweetDto {
    private String sweetName;

    private String sweetDetail;

    private String sweetPoint;

    private String sweetWarning;
}
