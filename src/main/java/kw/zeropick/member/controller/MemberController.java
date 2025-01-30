package kw.zeropick.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kw.zeropick.common.LoginUser;
import kw.zeropick.member.controller.response.CreateMemberResponse;
import kw.zeropick.member.controller.response.MemberFieldResponse;
import kw.zeropick.member.controller.response.MemberInfoResponse;
import kw.zeropick.member.controller.response.MypageInfoResponse;
import kw.zeropick.member.domain.Member;
import kw.zeropick.member.dto.MemberEmailDto;
import kw.zeropick.member.dto.MemberInfoChangeDto;
import kw.zeropick.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "member", description = "회원 관리 API")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(
            summary = "내 정보 조회",
            description = "마이페이지에서 내 정보들을 가져옵니다.")
    @GetMapping("/myPage/info")
    public ResponseEntity<MemberInfoResponse> getInfo() {
        Long loginUser = LoginUser.get().getId();
        MemberInfoResponse memberInfoResponse = memberService.getMemberInfo(loginUser);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberInfoResponse);
    }

    @Operation(
            summary = "내 정보 수정",
            description = "내 정보 수정 요청을 받아 수정된 정보를 반환합니다.")
    @PutMapping("/myPage/info")
    public ResponseEntity<MemberInfoResponse> changeMemberInfo(@RequestBody @Valid MemberInfoChangeDto memberInfoChangeDto) {
        Long loginUser = LoginUser.get().getId();
        MemberInfoResponse updatedInfo = memberService.updateMemberInfo(loginUser, memberInfoChangeDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedInfo);
    }

    @Operation(
            summary = "마이페이지 클릭시 정보 조회",
            description = "마이페이지 클릭 시 닉네임, 찜한 제품 수, 내가 쓴 리뷰 수를 반환합니다.")
    @GetMapping("/myPage")
    public ResponseEntity<MypageInfoResponse> getMypageInfo() {
        Long loginUser = LoginUser.get().getId();
        MypageInfoResponse mypageInfoResponse = memberService.getMypageInfo(loginUser);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mypageInfoResponse);
    }

    @Operation(
            summary = "내정보 조회 이메일 일치 확인",
            description = "내 정보를 조회를 위해 이메일 일치 여부를 확인합니다.")
    @PostMapping("/checkEmail")
    public ResponseEntity<Boolean> checkEmail(@RequestBody MemberEmailDto memberEmailDto) {
        Long loginUser = LoginUser.get().getId();
        Member member = memberService.getById(loginUser);

        return ResponseEntity.ok(member.getEmail().equals(memberEmailDto.getEmail()));
    }


}






