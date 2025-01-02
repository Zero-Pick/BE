package kw.zeropick.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPositiveTag is a Querydsl query type for PositiveTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPositiveTag extends EntityPathBase<PositiveTag> {

    private static final long serialVersionUID = -605296694L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPositiveTag positiveTag = new QPositiveTag("positiveTag");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QReview review;

    public final NumberPath<Integer> tagCount = createNumber("tagCount", Integer.class);

    public final EnumPath<PositiveTagEnum> tagEnum = createEnum("tagEnum", PositiveTagEnum.class);

    public final StringPath tagTitle = createString("tagTitle");

    public QPositiveTag(String variable) {
        this(PositiveTag.class, forVariable(variable), INITS);
    }

    public QPositiveTag(Path<? extends PositiveTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPositiveTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPositiveTag(PathMetadata metadata, PathInits inits) {
        this(PositiveTag.class, metadata, inits);
    }

    public QPositiveTag(Class<? extends PositiveTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}

