package kw.zeropick.registration.controller;

import io.swagger.v3.oas.annotations.Operation;
import kw.zeropick.common.LoginUser;
import kw.zeropick.member.domain.Member;
import kw.zeropick.member.service.MemberService;
import kw.zeropick.registration.controller.response.RegistrationResponse;
import kw.zeropick.registration.dto.RegistrationRequestDto;
import kw.zeropick.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final MemberService memberService;

    @PostMapping
    @Operation(summary = "제품 등록 요청 생성")
    public ResponseEntity<Object> registerProduct(
            @RequestBody RegistrationRequestDto requestDto) {

        Long memberId = LoginUser.get().getId();
        Member requestMember = memberService.getById(memberId);
        RegistrationResponse response = registrationService.registerProduct(requestMember, requestDto);

        return ResponseEntity.ok(response);
    }
}
