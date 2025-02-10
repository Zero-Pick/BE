package kw.zeropick.registration.repository;

import kw.zeropick.registration.domain.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {



}
