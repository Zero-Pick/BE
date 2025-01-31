package kw.zeropick.member.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
public class MaskingEmailResponse {
    private String maskEmail;

    @Builder
    public MaskingEmailResponse(String maskEmail) {
        this.maskEmail = maskEmail;
    }
}
