package kw.zeropick.product.repository;

import java.util.Optional;
import kw.zeropick.member.domain.Member;
import kw.zeropick.product.domain.Bookmark;
import kw.zeropick.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkJpaRepository extends JpaRepository<Bookmark, Long> {
    Boolean existsByProductAndMember(Product product, Member member);
    Optional<Bookmark> findByProductAndMember(Product product, Member member);
}
