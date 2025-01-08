package kw.zeropick.product.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompare is a Querydsl query type for Compare
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompare extends EntityPathBase<Compare> {

    private static final long serialVersionUID = 1378814141L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompare compare = new QCompare("compare");

    public final kw.zeropick.common.domain.QBaseEntity _super = new kw.zeropick.common.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final kw.zeropick.member.domain.QMember member;

    public final QProduct product;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCompare(String variable) {
        this(Compare.class, forVariable(variable), INITS);
    }

    public QCompare(Path<? extends Compare> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompare(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompare(PathMetadata metadata, PathInits inits) {
        this(Compare.class, metadata, inits);
    }

    public QCompare(Class<? extends Compare> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new kw.zeropick.member.domain.QMember(forProperty("member")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

