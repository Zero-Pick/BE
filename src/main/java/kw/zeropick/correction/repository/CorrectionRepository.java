package kw.zeropick.correction.repository;

import java.util.List;
import kw.zeropick.correction.domain.Correction;
import kw.zeropick.correction.dto.response.CorrectionResponse;
import kw.zeropick.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorrectionRepository extends JpaRepository<Correction, Long> {
    List<Correction> findByMember(Member member);
}
