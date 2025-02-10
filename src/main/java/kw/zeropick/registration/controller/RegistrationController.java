package kw.zeropick.registration.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kw.zeropick.common.LoginUser;
import kw.zeropick.member.domain.Member;
import kw.zeropick.member.service.MemberService;
import kw.zeropick.payload.ApiResponse;
import kw.zeropick.registration.controller.response.RegistrationResponse;
import kw.zeropick.registration.dto.RegistrationRequestDto;
import kw.zeropick.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "product", description = "상품 등록 요청 API")
@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final MemberService memberService;

    @PostMapping
    @Operation(summary = "제품 등록 요청 생성")
    public ResponseEntity<ApiResponse> registerProduct(
            @RequestBody RegistrationRequestDto requestDto) {

        Long memberId = LoginUser.get().getId();
        Member requestMember = memberService.getById(memberId);
        RegistrationResponse response = registrationService.registerProduct(requestMember, requestDto);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .check(true)
                        .information(response)
                        .build()
        );
    }

    @GetMapping
    @Operation(summary = "제품 등록 요청 조회")
    public ResponseEntity<ApiResponse> getAllRegistrations() {
        Long memberId = LoginUser.get().getId();
        Member requestMember = memberService.getById(memberId);
        List<RegistrationResponse> responseList = registrationService.getAllRegistrations(requestMember);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .check(true)
                        .information(responseList)
                        .build()
        );
    }
}
