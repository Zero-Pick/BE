package kw.zeropick.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kw.zeropick.common.converter.StringListToStringConverter;
import kw.zeropick.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private String password;

    @Convert(converter = StringListToStringConverter.class)
    private List<String> memberPositiveTag;

    @NotNull
    @Enumerated(EnumType.STRING)
    private State userState;

    private LocalDate deleteDate;
}
