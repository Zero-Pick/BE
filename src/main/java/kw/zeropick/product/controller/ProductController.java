package kw.zeropick.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import kw.zeropick.common.LoginUser;
import kw.zeropick.payload.ApiResponse;
import kw.zeropick.product.dto.ProductDto;
import kw.zeropick.product.service.ProductService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
//    상품 찜 하기
    @Operation(summary = "상품 찜 하기", description = "상품 좋아요 요청(로그인 기능 적용 전이므로 1번유저 고정)")
    @PostMapping("/bookmark/{productId}")
    public ResponseEntity<ApiResponse> productBookmark(@PathVariable Long productId) {
        Long memberId = LoginUser.get().getId();
        try {
            productService.bookmark(productId, memberId);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .check(true)
                            .information("Product bookmark successfully")
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

//    상품 찜 취소
    @Operation(summary = "상품 찜 취소", description = "상품 찜 취소 요청(로그인 기능 적용 전이므로 1번유저 고정)")
    @DeleteMapping("/bookmark/{productId}")
    public ResponseEntity<ApiResponse> productBookmarkUndo(@PathVariable Long productId) {
        Long memberId = LoginUser.get().getId();
        try {
            productService.undoBookmark(productId, memberId);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .check(true)
                            .information("Product bookmark deleted successfully")
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

//    좋아요 상품 조회 (페이징 필)


//    상품 비교 넣기
    @Operation(summary = "상품 비교 추가 하기", description = "상품 비교 추가 요청(로그인 기능 적용 전이므로 1번유저 고정)")
    @PostMapping("/compare/{productId}")
    public ResponseEntity<ApiResponse> productCompare(@PathVariable Long productId) {
        Long memberId = LoginUser.get().getId();
        try {
            productService.compare(productId, memberId);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .check(true)
                            .information("Product compare successfully")
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

//    상품 비교 빼기
    @Operation(summary = "상품 비교 취소", description = "상품 비교 취소 요청(로그인 기능 적용 전이므로 1번유저 고정)")
    @DeleteMapping("/compare/{productId}")
    public ResponseEntity<ApiResponse> productCompareUndo(@PathVariable Long productId) {
        Long memberId = LoginUser.get().getId();
        try {
            productService.undoCompare(productId, memberId);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .check(true)
                            .information("Product compare deleted successfully")
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

//    비교 상품 조회
    @Operation(summary = "비교에 넣은 상품 보기", description = "비교에 넣은 상품 보기(로그인 기능 적용 전이므로 1번유저 고정)")
    @GetMapping("/compare")
    public ResponseEntity<ApiResponse> compare(){
        Long memberId = LoginUser.get().getId();
        try {
            List<ProductDto> productDtos = productService.compareProductList(memberId);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .check(true)
                            .information(productDtos)
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
}
