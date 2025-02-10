package kw.zeropick.registration.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRegistration is a Querydsl query type for Registration
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRegistration extends EntityPathBase<Registration> {

    private static final long serialVersionUID = -1263438447L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRegistration registration = new QRegistration("registration");

    public final kw.zeropick.common.domain.QBaseEntity _super = new kw.zeropick.common.domain.QBaseEntity(this);

    public final StringPath additional = createString("additional");

    public final StringPath brand = createString("brand");

    public final EnumPath<kw.zeropick.product.domain.Category> category = createEnum("category", kw.zeropick.product.domain.Category.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath ingredient = createString("ingredient");

    public final kw.zeropick.member.domain.QMember member;

    public final StringPath productName = createString("productName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QRegistration(String variable) {
        this(Registration.class, forVariable(variable), INITS);
    }

    public QRegistration(Path<? extends Registration> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRegistration(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRegistration(PathMetadata metadata, PathInits inits) {
        this(Registration.class, metadata, inits);
    }

    public QRegistration(Class<? extends Registration> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new kw.zeropick.member.domain.QMember(forProperty("member")) : null;
    }

}

