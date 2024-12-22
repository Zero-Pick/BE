package kw.zeropick.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kw.zeropick.common.LoginUser;
import kw.zeropick.payload.ApiResponse;
import kw.zeropick.product.service.ProductService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @Operation(summary = "상품 찜 하기", description = "상품 좋아요 요청(로그인 기능 적용 전이므로 1번유저 고정)")
    @PostMapping("/bookmark/{productId}")
    public ResponseEntity<ApiResponse> productBookmark(@PathVariable Long productId) {
        Long memberId = LoginUser.get().getId();
        try {
            productService.bookmark(productId, memberId);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .check(true)
                            .information("Review liked successfully")
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    ApiResponse.builder()
                            .check(false)
                            .information(e.getMessage())
                            .build()
            );
        }
    }

//    상품 좋아요 취소


//    좋아요 상품 조회 (페이징 필)


//    상품 비교 넣기


//    상품 비교 빼기


//    비교 상품 조회


}
