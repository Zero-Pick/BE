package kw.zeropick.registration.service;

import kw.zeropick.member.domain.Member;
import kw.zeropick.product.domain.Category;
import kw.zeropick.registration.controller.response.RegistrationResponse;
import kw.zeropick.registration.domain.Registration;
import kw.zeropick.registration.dto.RegistrationRequestDto;
import kw.zeropick.registration.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    @Transactional
    public RegistrationResponse registerProduct(Member member, RegistrationRequestDto requestDto) {
        Registration registration = Registration.builder()
                .member(member)
                .brand(requestDto.getBrand())
                .productName(requestDto.getProductName())
                .category(Category.valueOf(requestDto.getCategory()))
                .ingredient(requestDto.getIngredient())
                .additional(requestDto.getAdditional())
                .build();

        Registration savedRegistration = registrationRepository.save(registration);

        return new RegistrationResponse(savedRegistration);
    }

}
