package kw.zeropick.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.Expressions;
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
import org.springframework.util.StringUtils;


@RequiredArgsConstructor
public class ProductQueryDslRepositoryImpl implements ProductQueryDslRepository {
    private final JPAQueryFactory queryFactory;

// ProductQueryDslRepositoryImpl.java

    @Override
    public Page<Product> searchProducts(ProductSearchRequest request, Pageable pageable) {
        QProduct product = QProduct.product;

        BooleanBuilder builder = new BooleanBuilder();

        // 상품명 또는 브랜드명 검색
        if (StringUtils.hasText(request.getKeyword())) {
            builder.and(
                    product.productName.containsIgnoreCase(request.getKeyword())
                            .or(product.brand.containsIgnoreCase(request.getKeyword()))
            );
        }

        // zerosugar 조건
        if (request.getZeroSugar() != null) {
            builder.and(product.zeroSugar.eq(request.getZeroSugar()));
        }

        // zerokcal 조건
        if (request.getZeroKcal() != null) {
            builder.and(product.zeroKcal.eq(request.getZeroKcal()));
        }

        // bloodSugar 조건
        if (request.getBloodSugar() != null) {
            builder.and(product.bloodSugar.eq(request.getBloodSugar()));
        }

        // 인공감미료 조건
        if (Boolean.TRUE.equals(request.getExceptErythritol())) {
            builder.and(product.ingredient.erythritol.isNull());
        }
        if (Boolean.TRUE.equals(request.getExceptAllulose())) {
            builder.and(product.ingredient.allulose.isNull());
        }

        // 태그 조건 추가
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            BooleanBuilder tagsCondition = new BooleanBuilder();
            for (PositiveTagEnum tag : request.getTags()) {
                tagsCondition.or(Expressions.stringTemplate(
                        "cast({0} as text)",
                        product.tags
                ).like("%" + tag.name() + "%"));
            }
            builder.and(tagsCondition);
        }

        // 카테고리 조건 추가
        if (request.getCategory() != null) {
            builder.and(product.category.eq(request.getCategory()));
        }

        // 정렬 기준 추가
        JPAQuery<Product> query = queryFactory
                .selectFrom(product)
                .leftJoin(product.ingredient).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        if (request.getSort() != null) {
            switch (request.getSort()) {
                case POPULARITY:
                    query.orderBy(product.popularity.desc());
                    break;
                case NEWEST:
                    query.orderBy(product.createdAt.desc());
                    break;
                case MOST_REVIEWED:
                    query.orderBy(product.reviewCount.desc());
                    break;
                case HIGHEST_RATED:
                    query.orderBy(product.starRate.desc());
                    break;
                case LOWEST_RATED:
                    query.orderBy(product.starRate.asc());
                    break;
            }
        }

        // 쿼리 실행
        QueryResults<Product> results = query.fetchResults();
        List<Product> products = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(products, pageable, total);
    }



}