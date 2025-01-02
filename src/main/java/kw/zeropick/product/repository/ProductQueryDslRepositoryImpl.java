package kw.zeropick.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import kw.zeropick.product.domain.Product;
import kw.zeropick.product.domain.QProduct;
import kw.zeropick.product.dto.request.ProductSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class ProductQueryDslRepositoryImpl implements ProductQueryDslRepository {
    private final JPAQueryFactory queryFactory;

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

        // 인공감미료 조건1
        if (Boolean.TRUE.equals(request.getExceptErythritol())) {
            builder.and(product.ingredient.erythritol.isNull());
        }

        // 인공감미료 조건2
        if (Boolean.TRUE.equals(request.getExceptAllulose())) {
            builder.and(product.ingredient.allulose.isNull());
        }

        // 쿼리 실행
        QueryResults<Product> results = queryFactory
                .selectFrom(product)
                .leftJoin(product.ingredient).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Product> products = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(products, pageable, total);
    }

}
