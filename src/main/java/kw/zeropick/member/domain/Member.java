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


    @Convert(converter = StringListToStringConverter.class)
    private List<String> field;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MarketingAgree marketingAgree;

    @NotNull
    @Enumerated(EnumType.STRING)
    private State userState;

    private LocalDate deleteDate;


    public Member( String email, String name, String phoneNumber, LocalDate birthDate, String password, MarketingAgree marketingAgree, State userState) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.password = password;
        this.marketingAgree = marketingAgree;
        this.userState = userState;
    }

    public void changeFieldInfo(List<String> field){
        this.field = field;
    }

    public void changeMemberInfo(String phoneNumber, LocalDate birthDate, MarketingAgree marketingAgree){
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.marketingAgree = marketingAgree;
    }


}
