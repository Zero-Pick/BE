package kw.zeropick.member.dto;

import jakarta.validation.constraints.NotNull;
import kw.zeropick.member.domain.MarketingAgree;
import kw.zeropick.member.domain.Member;
import kw.zeropick.member.domain.State;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MemberJoinDto {

    @NotNull
    private String email;
    @NotNull
    private String nickname;
    @NotNull
    private String phoneNumber;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private String password;
    @NotNull
    private String passwordConfirm;
    @NotNull
    private State userState;

//    @Builder
//    public MemberJoinDto(String email, String name, String phoneNumber, LocalDate birthDate,
//                         String password, Boolean marketingAgree, State userState) {
//        this.email = email;
//        this.name = name;
//        this.phoneNumber = phoneNumber;
//        this.birthDate = birthDate;
//        this.password = password;
//        this.marketingAgree = marketingAgree;
//        this.userState = userState;
//    }

    @Builder
    public MemberJoinDto(String email, String nickname, String phoneNumber, LocalDate birthDate,
                         String password, String passwordConfirm, State userState) {
        this.email = email;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.userState = userState;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .birthDate(birthDate)
                .password(password)
                .userState(userState)
                .build();
    }

}
