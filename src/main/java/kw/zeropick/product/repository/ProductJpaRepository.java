package kw.zeropick.product.repository;

import kw.zeropick.product.domain.Product;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<Product, Long>, ProductQueryDslRepository {
    @Query(value = "SELECT p FROM Product p ORDER BY p.popularity DESC")
    List<Product> findTopProductsByPopularity(); // 모든 상품을 가져오되 인기순으로 정렬
    List<Product> findAll();
}
