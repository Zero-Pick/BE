package kw.zeropick.correction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import kw.zeropick.common.LoginUser;
import kw.zeropick.correction.dto.request.CorrectionRequest;
import kw.zeropick.correction.dto.response.CorrectionResponse;
import kw.zeropick.correction.service.CorrectionService;
import kw.zeropick.member.domain.Member;
import kw.zeropick.member.service.MemberService;
import kw.zeropick.payload.ApiResponse;
import kw.zeropick.registration.controller.response.RegistrationResponse;
import kw.zeropick.registration.dto.RegistrationRequestDto;
import kw.zeropick.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "correction", description = "상품 수정 요청 API")
@RestController
@RequestMapping("/correction")
@RequiredArgsConstructor
public class CorrectionController {
    private final CorrectionService correctionService;
    private final MemberService memberService;

    @PostMapping
    @Operation(summary = "제품 수정 요청 생성")
    public ResponseEntity<ApiResponse> correctionProduct(
            @RequestPart(value = "correction") CorrectionRequest request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        Long memberId = 1L;

        CorrectionResponse response = correctionService.correctionProduct(memberId, request.getProductId(), request, files);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .check(true)
                        .information(response)
                        .build()
        );
    }

    @GetMapping
    @Operation(summary = "제품 수정 요청 조회")
    public ResponseEntity<ApiResponse> getAllCorrections() {
        Long memberId = 1L;
        Member requestMember = memberService.getById(memberId);
        List<CorrectionResponse> correctionResponses = correctionService.getAllCorrections(requestMember);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .check(true)
                        .information(correctionResponses)
                        .build()
        );
    }

    @DeleteMapping("/{correctionId}")
    @Operation(summary = "제품 수정 요청 철회")
    public ResponseEntity<ApiResponse> productBookmarkUndo(@PathVariable Long correctionId) {
        Long memberId = LoginUser.get().getId();
        try {
            correctionService.deleteCorrection(correctionId, memberId);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .check(true)
                            .information("Product correction deleted successfully")
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
