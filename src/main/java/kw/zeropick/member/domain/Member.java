package kw.zeropick.member.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kw.zeropick.common.converter.StringListToStringConverter;
import lombok.*;

import java.time.LocalDate;
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

    private String socialId;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private String password;


//    @Convert(converter = StringListToStringConverter.class)
//    private List<String> field;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MarketingAgree marketingAgree;

    @NotNull
    @Enumerated(EnumType.STRING)
    private State userState;

    private LocalDate deleteDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String refreshToken;


    public Member( String email, String name, String phoneNumber, LocalDate birthDate, String password, MarketingAgree marketingAgree, State userState) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.password = password;
        this.marketingAgree = marketingAgree;
        this.userState = userState;
    }

//    public void changeFieldInfo(List<String> field){
//        this.field = field;
//    }

    public void changeMemberInfo(String phoneNumber, LocalDate birthDate, MarketingAgree marketingAgree){
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.marketingAgree = marketingAgree;
    }

    public void activate() {
        this.userState = State.ACTIVATE;
        this.deleteDate = null;
    }

    public void setSocialId(String kakaoId) {
        this.socialId = kakaoId;
    }

    public void setEmail(String email) {this.email = email;}

    public void setName(String name) {this.name = name;}
    public void setRole(Role role) {this.role = role;}

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public void setRefreshToken(String refreshToken){ this.refreshToken = refreshToken;}
    public void setSocialType(SocialType type){this.socialType = type;}
    public void setUserState(State state){this.userState = state;}
}
