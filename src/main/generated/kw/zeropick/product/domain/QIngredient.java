package kw.zeropick.product.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIngredient is a Querydsl query type for Ingredient
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIngredient extends EntityPathBase<Ingredient> {

    private static final long serialVersionUID = -176883975L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIngredient ingredient = new QIngredient("ingredient");

    public final NumberPath<Float> allulose = createNumber("allulose", Float.class);

    public final NumberPath<Float> carb = createNumber("carb", Float.class);

    public final NumberPath<Float> cholesterol = createNumber("cholesterol", Float.class);

    public final NumberPath<Float> erythritol = createNumber("erythritol", Float.class);

    public final NumberPath<Float> fat = createNumber("fat", Float.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Float> kcal = createNumber("kcal", Float.class);

    public final NumberPath<Float> natrium = createNumber("natrium", Float.class);

    public final QProduct product;

    public final NumberPath<Float> protein = createNumber("protein", Float.class);

    public final NumberPath<Float> saturatedFat = createNumber("saturatedFat", Float.class);

    public final NumberPath<Float> sweet = createNumber("sweet", Float.class);

    public final NumberPath<Float> transFat = createNumber("transFat", Float.class);

    public QIngredient(String variable) {
        this(Ingredient.class, forVariable(variable), INITS);
    }

    public QIngredient(Path<? extends Ingredient> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIngredient(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIngredient(PathMetadata metadata, PathInits inits) {
        this(Ingredient.class, metadata, inits);
    }

    public QIngredient(Class<? extends Ingredient> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

