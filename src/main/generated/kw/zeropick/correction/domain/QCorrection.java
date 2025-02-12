package kw.zeropick.correction.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCorrection is a Querydsl query type for Correction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCorrection extends EntityPathBase<Correction> {

    private static final long serialVersionUID = 1655451227L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCorrection correction = new QCorrection("correction");

    public final kw.zeropick.common.domain.QBaseEntity _super = new kw.zeropick.common.domain.QBaseEntity(this);

    public final StringPath additional = createString("additional");

    public final EnumPath<CorrectionStatus> correctionStatus = createEnum("correctionStatus", CorrectionStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<String, StringPath> imageUrls = this.<String, StringPath>createList("imageUrls", String.class, StringPath.class, PathInits.DIRECT2);

    public final kw.zeropick.member.domain.QMember member;

    public final kw.zeropick.product.domain.QProduct product;

    public final StringPath rejectionReason = createString("rejectionReason");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCorrection(String variable) {
        this(Correction.class, forVariable(variable), INITS);
    }

    public QCorrection(Path<? extends Correction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCorrection(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCorrection(PathMetadata metadata, PathInits inits) {
        this(Correction.class, metadata, inits);
    }

    public QCorrection(Class<? extends Correction> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new kw.zeropick.member.domain.QMember(forProperty("member")) : null;
        this.product = inits.isInitialized("product") ? new kw.zeropick.product.domain.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

