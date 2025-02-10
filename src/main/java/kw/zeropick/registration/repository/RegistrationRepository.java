package kw.zeropick.registration.repository;

import kw.zeropick.member.domain.Member;
import kw.zeropick.registration.domain.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByMember(Member member);
}
