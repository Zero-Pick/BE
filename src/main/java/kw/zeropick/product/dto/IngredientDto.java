package kw.zeropick.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class IngredientDto {
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
}
