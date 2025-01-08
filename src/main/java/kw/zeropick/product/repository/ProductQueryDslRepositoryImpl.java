package kw.zeropick.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import kw.zeropick.product.domain.Product;
import kw.zeropick.product.domain.QProduct;
import kw.zeropick.product.dto.request.ProductSearchRequest;
import kw.zeropick.review.domain.PositiveTagEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


@RequiredArgsConstructor
public class ProductQueryDslRepositoryImpl implements ProductQueryDslRepository {
    private final JPAQueryFactory queryFactory;
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Page<Product> searchProducts(ProductSearchRequest request, Pageable pageable) {
        String sql = "SELECT * FROM product WHERE 1=1";
        String countSql = "SELECT COUNT(*) FROM product WHERE 1=1";

        // 기본 조건 추가
        if (request.getKeyword() != null) {
            sql += " AND (LOWER(product_name) LIKE :keyword OR LOWER(brand) LIKE :keyword)";
            countSql += " AND (LOWER(product_name) LIKE :keyword OR LOWER(brand) LIKE :keyword)";
        }
        if (request.getZeroSugar() != null) {
            sql += " AND zero_sugar = :zeroSugar";
            countSql += " AND zero_sugar = :zeroSugar";
        }
        if (request.getZeroKcal() != null) {
            sql += " AND zero_kcal = :zeroKcal";
            countSql += " AND zero_kcal = :zeroKcal";
        }

        // 태그 조건 추가 (JSON_CONTAINS 사용)
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            for (int i = 0; i < request.getTags().size(); i++) {
                sql += " AND JSON_CONTAINS(tags, :tag" + i + ")";
                countSql += " AND JSON_CONTAINS(tags, :tag" + i + ")";
            }
        }

        // 정렬 및 페이징
        sql += " ORDER BY product_id LIMIT :offset, :limit";

        Query query = entityManager.createNativeQuery(sql, Product.class);

        // 파라미터 바인딩
        if (request.getKeyword() != null) {
            query.setParameter("keyword", "%" + request.getKeyword().toLowerCase() + "%");
        }
        if (request.getZeroSugar() != null) {
            query.setParameter("zeroSugar", request.getZeroSugar());
        }
        if (request.getZeroKcal() != null) {
            query.setParameter("zeroKcal", request.getZeroKcal());
        }
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            for (int i = 0; i < request.getTags().size(); i++) {
                query.setParameter("tag" + i, "\"" + request.getTags().get(i).name() + "\"");
            }
        }
        query.setParameter("offset", pageable.getOffset());
        query.setParameter("limit", pageable.getPageSize());

        List<Product> products = query.getResultList();

        // 총 개수 쿼리
        Query countQuery = entityManager.createNativeQuery(countSql);
        if (request.getKeyword() != null) {
            countQuery.setParameter("keyword", "%" + request.getKeyword().toLowerCase() + "%");
        }
        if (request.getZeroSugar() != null) {
            countQuery.setParameter("zeroSugar", request.getZeroSugar());
        }
        if (request.getZeroKcal() != null) {
            countQuery.setParameter("zeroKcal", request.getZeroKcal());
        }
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            for (int i = 0; i < request.getTags().size(); i++) {
                countQuery.setParameter("tag" + i, "\"" + request.getTags().get(i).name() + "\"");
            }
        }

        Long total = ((Number) countQuery.getSingleResult()).longValue();

        return new PageImpl<>(products, pageable, total);
    }

}