package kw.zeropick.home.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import kw.zeropick.common.LoginUser;
import kw.zeropick.home.dto.response.ProductBestResponse;
import kw.zeropick.home.service.HomeService;
import kw.zeropick.payload.ApiResponse;
import kw.zeropick.product.domain.Category;
import kw.zeropick.product.dto.ProductDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "home", description = "홈 화면 API")
@Builder
@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @Operation(summary = "홈 화면 조회", description = "홈 화면을 조회합니다.")
    @PostMapping
    public ResponseEntity<String> home(@RequestHeader("Authorization") String token) {

        if (token == null) {

        } else {

        }

        return ResponseEntity.ok("리뷰가 성공적으로 등록되었습니다.");
    }

    @Operation(summary = "홈 카테고리 베스트 조회", description = "카테고리 베스트를 조회합니다.")
    @GetMapping("/category")
    public ResponseEntity<ApiResponse> homeCategory(@RequestParam Category category) {
        Long memberId = LoginUser.get().getId();
        try {
            List<ProductBestResponse> productBestResponses = homeService.categoryBest(memberId, category);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .check(true)
                            .information(productBestResponses)
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