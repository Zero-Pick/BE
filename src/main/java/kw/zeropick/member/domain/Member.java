package kw.zeropick.member.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kw.zeropick.common.converter.StringListToStringConverter;
import kw.zeropick.registration.domain.Registration;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

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

    //관심 있는 제로 종류(제로 슈거 / 제로 칼로리)
    @Enumerated(EnumType.STRING)
    private MemberInterest interest;

    //당뇨 환자 여부
    private Boolean diabetes;

    @NotNull
    @Enumerated(EnumType.STRING)
    private State userState;

    private LocalDate deleteDate;


    public Member( String email, String nickname, String phoneNumber, LocalDate birthDate, String password, State userState) {
        this.email = email;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.password = password;
        this.userState = userState;
    }


    public void changeMemberInfo(String phoneNumber, LocalDate birthDate){
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void setInterest(MemberInterest interest){
        this.interest = interest;
    }

    public void setDiabetes(Boolean diabetes){
        this.diabetes = diabetes;
    }


}
