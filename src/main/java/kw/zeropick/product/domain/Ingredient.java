package kw.zeropick.product.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long id;

    private Float kcal;

    private Float carb;

    private Float sweet;

    private Float protein;

    private Float fat;

    private Float transFat;

    private Float saturatedFat;

    private Float natrium;

    private Float cholesterol;

    private Float allulose;

    private Float erythritol;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id", unique = true)
    private Product product;
}
