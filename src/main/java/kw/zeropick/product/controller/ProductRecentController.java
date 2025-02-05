package kw.zeropick.product.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import kw.zeropick.payload.ApiResponse;
import kw.zeropick.product.dto.ProductDto;
import kw.zeropick.product.dto.ProductRecentDto;
import kw.zeropick.product.service.ProductRecentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recent")
@RequiredArgsConstructor
public class ProductRecentController {
    private final ProductRecentService recentService;

    @PostMapping("/{productId}")
    public ResponseEntity<ApiResponse> addRecentProduct(
            @PathVariable Long productId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            // 쿠키 값 가져오기
            String cookieValue = getCookieValue(request, "recentProducts");

            // 서비스 호출
            String updatedCookieValue = recentService.addRecentProduct(productId, cookieValue);

            // 쿠키 저장
            Cookie cookie = new Cookie("recentProducts", updatedCookieValue);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7일
            response.addCookie(cookie);

            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .check(true)
                            .information("상품이 최근 본 목록에 추가되었습니다.")
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

    @GetMapping
    public ResponseEntity<ApiResponse> getRecentProducts(HttpServletRequest request) {
        try {
            String cookieValue = getCookieValue(request, "recentProducts");

            // 서비스 호출
            List<ProductRecentDto> recentProducts = recentService.getRecentProducts(cookieValue);

            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .check(true)
                            .information(recentProducts)
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

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}