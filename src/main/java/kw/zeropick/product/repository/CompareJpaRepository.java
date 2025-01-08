package kw.zeropick.product.repository;

import java.util.List;
import java.util.Optional;
import kw.zeropick.member.domain.Member;
import kw.zeropick.product.domain.Compare;
import kw.zeropick.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompareJpaRepository extends JpaRepository<Compare, Long> {
    Boolean existsByProductAndMember(Product product, Member member);
    Optional<Compare> findByProductAndMember(Product product, Member member);
    List<Compare> findAllByMemberId(Long memberId);
    boolean existsByProductIdAndMemberId(Long productId, Long memberId);

}
