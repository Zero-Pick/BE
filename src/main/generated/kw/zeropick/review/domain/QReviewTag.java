package kw.zeropick.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewTag is a Querydsl query type for ReviewTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewTag extends EntityPathBase<ReviewTag> {

    private static final long serialVersionUID = -1487355829L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewTag reviewTag = new QReviewTag("reviewTag");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<NegativeTagEnum> negativeTagEnum = createEnum("negativeTagEnum", NegativeTagEnum.class);

    public final BooleanPath positiveNegative = createBoolean("positiveNegative");

    public final EnumPath<PositiveTagEnum> positiveTagEnum = createEnum("positiveTagEnum", PositiveTagEnum.class);

    public final NumberPath<Integer> productId = createNumber("productId", Integer.class);

    public final QReview review;

    public final NumberPath<Integer> tagCount = createNumber("tagCount", Integer.class);

    public final StringPath tagTitle = createString("tagTitle");

    public QReviewTag(String variable) {
        this(ReviewTag.class, forVariable(variable), INITS);
    }

    public QReviewTag(Path<? extends ReviewTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewTag(PathMetadata metadata, PathInits inits) {
        this(ReviewTag.class, metadata, inits);
    }

    public QReviewTag(Class<? extends ReviewTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}

