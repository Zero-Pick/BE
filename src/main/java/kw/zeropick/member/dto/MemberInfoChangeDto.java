package kw.zeropick.member.dto;


import kw.zeropick.member.domain.MemberInterest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MemberInfoChangeDto {

    private String email;
    private String nickname;
    private MemberInterest interest;
    private Boolean diabetes;

    @Builder
    public MemberInfoChangeDto(String email, String nickname, MemberInterest interest, Boolean diabetes) {
        this.email = email;
        this.nickname = nickname;
        this.interest = interest;
        this.diabetes = diabetes;
    }

}
