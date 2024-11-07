package kw.zeropick.member.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Member {
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
}
