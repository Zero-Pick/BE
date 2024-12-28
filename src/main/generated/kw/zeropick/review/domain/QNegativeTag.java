package kw.zeropick.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNegativeTag is a Querydsl query type for NegativeTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNegativeTag extends EntityPathBase<NegativeTag> {

    private static final long serialVersionUID = -184189938L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNegativeTag negativeTag = new QNegativeTag("negativeTag");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QReview review;

    public final NumberPath<Integer> tagCount = createNumber("tagCount", Integer.class);

    public final EnumPath<NegativeTagEnum> tagNnum = createEnum("tagNnum", NegativeTagEnum.class);

    public final StringPath tagTitle = createString("tagTitle");

    public QNegativeTag(String variable) {
        this(NegativeTag.class, forVariable(variable), INITS);
    }

    public QNegativeTag(Path<? extends NegativeTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNegativeTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNegativeTag(PathMetadata metadata, PathInits inits) {
        this(NegativeTag.class, metadata, inits);
    }

    public QNegativeTag(Class<? extends NegativeTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}

