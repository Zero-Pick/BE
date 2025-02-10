package kw.zeropick.registration.dto;

import jakarta.validation.constraints.NotNull;
import kw.zeropick.product.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class RegistrationRequestDto {
    @NotNull
    private String brand;
    @NotNull
    private String productName;
    @NotNull
    private Category category;
    private String ingredient;
    private String additional;

    public RegistrationRequestDto(String brand, String productName, Category category, String ingredient, String additional) {
        this.brand = brand;
        this.productName = productName;
        this.category = category;
        this.ingredient = ingredient;
        this.additional = additional;
    }
}
