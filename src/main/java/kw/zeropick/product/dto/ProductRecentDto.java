package kw.zeropick.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProductRecentDto {
    private Long id;

    private String productName;

    private String imageUrl;
}
