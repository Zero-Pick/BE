package kw.zeropick.member.controller.response;


import kw.zeropick.member.domain.MemberInterest;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberInfoResponse {
    private String email;
    private String nickname;
    private MemberInterest interest;
    private Boolean diabetes;

    @Builder
    public MemberInfoResponse(String email, String nickname, MemberInterest interest, Boolean diabetes) {
        this.email = email;
        this.nickname = nickname;
        this.interest = interest;
        this.diabetes = diabetes;
    }
}
