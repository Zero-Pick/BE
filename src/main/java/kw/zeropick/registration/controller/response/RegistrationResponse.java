package kw.zeropick.registration.controller.response;

import kw.zeropick.registration.domain.Registration;
import lombok.Getter;

@Getter
public class RegistrationResponse {
    private Long id;
    private String brand;
    private String productName;
    private String category;
    private String ingredient;
    private String additional;

    public RegistrationResponse(Registration registration) {
        this.id = registration.getId();
        this.brand = registration.getBrand();
        this.productName = registration.getProductName();
        this.category = registration.getCategory().name();
        this.ingredient = registration.getIngredient();
        this.additional = registration.getAdditional();
    }
}
