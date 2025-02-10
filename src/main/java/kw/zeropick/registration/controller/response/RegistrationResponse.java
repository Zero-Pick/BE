package kw.zeropick.registration.controller.response;

import kw.zeropick.registration.domain.Registration;
import kw.zeropick.registration.domain.RegistrationStatus;
import lombok.Getter;

@Getter
public class RegistrationResponse {
    private final Long id;
    private final String brand;
    private final String productName;
    private final String category;
    private final String ingredient;
    private final String additional;
    private final RegistrationStatus registrationStatus;
    private final String rejectionReason;

    public RegistrationResponse(Registration registration) {
        this.id = registration.getId();
        this.brand = registration.getBrand();
        this.productName = registration.getProductName();
        this.category = registration.getCategory().name();
        this.ingredient = registration.getIngredient();
        this.additional = registration.getAdditional();
        this.registrationStatus = registration.getRegistrationStatus();
        this.rejectionReason = registration.getRejectionReason();
    }
}
