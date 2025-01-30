package kw.zeropick.product.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 118855879L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final kw.zeropick.common.domain.QBaseEntity _super = new kw.zeropick.common.domain.QBaseEntity(this);

    public final StringPath bigCategory = createString("bigCategory");

    public final BooleanPath bloodSugar = createBoolean("bloodSugar");

    public final NumberPath<Integer> bookmarkCount = createNumber("bookmarkCount", Integer.class);

    public final StringPath brand = createString("brand");

    public final EnumPath<Category> category = createEnum("category", Category.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath foodName = createString("foodName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final QIngredient ingredient;

    public final ListPath<kw.zeropick.review.domain.NegativeTagEnum, EnumPath<kw.zeropick.review.domain.NegativeTagEnum>> negativeTop3tags = this.<kw.zeropick.review.domain.NegativeTagEnum, EnumPath<kw.zeropick.review.domain.NegativeTagEnum>>createList("negativeTop3tags", kw.zeropick.review.domain.NegativeTagEnum.class, EnumPath.class, PathInits.DIRECT2);

    public final NumberPath<Integer> popularity = createNumber("popularity", Integer.class);

    public final ListPath<kw.zeropick.review.domain.PositiveTagEnum, EnumPath<kw.zeropick.review.domain.PositiveTagEnum>> positiveTop3tags = this.<kw.zeropick.review.domain.PositiveTagEnum, EnumPath<kw.zeropick.review.domain.PositiveTagEnum>>createList("positiveTop3tags", kw.zeropick.review.domain.PositiveTagEnum.class, EnumPath.class, PathInits.DIRECT2);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath productName = createString("productName");

    public final NumberPath<Integer> reviewCount = createNumber("reviewCount", Integer.class);

    public final ListPath<kw.zeropick.review.domain.Review, kw.zeropick.review.domain.QReview> reviews = this.<kw.zeropick.review.domain.Review, kw.zeropick.review.domain.QReview>createList("reviews", kw.zeropick.review.domain.Review.class, kw.zeropick.review.domain.QReview.class, PathInits.DIRECT2);

    public final NumberPath<Double> starRate = createNumber("starRate", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public final BooleanPath zeroKcal = createBoolean("zeroKcal");

    public final BooleanPath zeroSugar = createBoolean("zeroSugar");

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ingredient = inits.isInitialized("ingredient") ? new QIngredient(forProperty("ingredient"), inits.get("ingredient")) : null;
    }

}

