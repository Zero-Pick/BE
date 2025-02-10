package kw.zeropick.registration.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegistrationRequestDto {
    private String brand;
    private String productName;
    private String category;
    private String ingredient;
    private String additional;
}
