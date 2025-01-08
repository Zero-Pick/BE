package kw.zeropick.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewTagMapping is a Querydsl query type for ReviewTagMapping
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewTagMapping extends EntityPathBase<ReviewTagMapping> {

    private static final long serialVersionUID = -882108925L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewTagMapping reviewTagMapping = new QReviewTagMapping("reviewTagMapping");

    public final kw.zeropick.common.domain.QBaseEntity _super = new kw.zeropick.common.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QReview review;

    public final QReviewTag reviewTag;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QReviewTagMapping(String variable) {
        this(ReviewTagMapping.class, forVariable(variable), INITS);
    }

    public QReviewTagMapping(Path<? extends ReviewTagMapping> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewTagMapping(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewTagMapping(PathMetadata metadata, PathInits inits) {
        this(ReviewTagMapping.class, metadata, inits);
    }

    public QReviewTagMapping(Class<? extends ReviewTagMapping> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
        this.reviewTag = inits.isInitialized("reviewTag") ? new QReviewTag(forProperty("reviewTag")) : null;
    }

}

