package kw.zeropick.product.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "product", description = "상품 관리 API")
@Builder
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
}
