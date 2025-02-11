package kw.zeropick.registration.service;

import kw.zeropick.member.domain.Member;
import kw.zeropick.product.domain.Category;
import kw.zeropick.registration.controller.response.RegistrationResponse;
import kw.zeropick.registration.domain.Registration;
import kw.zeropick.registration.domain.RegistrationStatus;
import kw.zeropick.registration.dto.RegistrationRequestDto;
import kw.zeropick.registration.repository.RegistrationRepository;
import kw.zeropick.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    private final S3Util s3Util;
    @Transactional
    public RegistrationResponse registerProduct(Member member, RegistrationRequestDto requestDto, List<MultipartFile> files) {

        List<String> images = new ArrayList<>();

        // 이미지 파일 처리 및 S3 업로드
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    String imageUrl = s3Util.upload(file);
                    System.out.println(imageUrl);
                    images.add(imageUrl);
                }
            }
        }

        Registration registration = Registration.builder()
                .member(member)
                .brand(requestDto.getBrand())
                .productName(requestDto.getProductName())
                .category(requestDto.getCategory())
                .ingredient(requestDto.getIngredient())
                .additional(requestDto.getAdditional())
                .registrationStatus(RegistrationStatus.PENDING)
                .imageUrls(images)
                .build();

        Registration savedRegistration = registrationRepository.save(registration);

        return new RegistrationResponse(savedRegistration);
    }

    @Transactional(readOnly = true)
    public List<RegistrationResponse> getAllRegistrations(Member member) {
        List<Registration> registrations = registrationRepository.findByMember(member);
        return registrations.stream()
                .map(RegistrationResponse::new)
                .collect(Collectors.toList());
    }

}
