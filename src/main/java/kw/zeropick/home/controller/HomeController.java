package kw.zeropick.home.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "home", description = "홈 화면 API")
@Builder
@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    @Operation(summary = "홈 화면 조회", description = "홈 화면을 조회합니다.")
    @PostMapping
    public ResponseEntity<String> home(@RequestHeader("Authorization") String token) {

        if (token == null) {

        } else {

        }

        return ResponseEntity.ok("리뷰가 성공적으로 등록되었습니다.");
    }
}