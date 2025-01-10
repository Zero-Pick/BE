package kw.zeropick.home.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import kw.zeropick.common.LoginUser;
import kw.zeropick.home.dto.response.HomeResponse;
import kw.zeropick.home.dto.response.ProductBestResponse;
import kw.zeropick.home.service.HomeService;
import kw.zeropick.payload.ApiResponse;
import kw.zeropick.product.domain.Category;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@Tag(name = "home", description = "홈 화면 API")
@Builder
@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @Operation(summary = "홈 화면 조회", description = "홈 화면을 조회합니다.")
    @GetMapping
    public ResponseEntity<HomeResponse> home(@RequestHeader(value = "Authorization", required = false) String token) {
        HomeResponse homeResponse = homeService.getHomeResponse(token);
        return ResponseEntity.ok(homeResponse);
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