package kw.zeropick.product.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kw.zeropick.product.service.ProductService;
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
    private final ProductService productService;

//    비로그인
//    상품 상세정보 조회 (조회수 카운트)

//    검색기능(필터가 많아 나중에)


//    로그인 필요
//    상품 좋아요 하기


//    상품 좋아요 취소


//    좋아요 상품 조회 (페이징 필)


//    상품 비교 넣기


//    상품 비교 빼기


//    비교 상품 조회


}
