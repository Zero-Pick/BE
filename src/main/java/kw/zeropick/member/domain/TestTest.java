package kw.zeropick.member.domain;

import jakarta.persistence.*;

@Entity
public class TestTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
}
