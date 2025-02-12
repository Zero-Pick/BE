package kw.zeropick.correction.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import kw.zeropick.correction.domain.Correction;
import kw.zeropick.correction.domain.CorrectionStatus;
import kw.zeropick.correction.dto.request.CorrectionRequest;
import kw.zeropick.correction.dto.response.CorrectionResponse;
import kw.zeropick.correction.repository.CorrectionRepository;
import kw.zeropick.member.domain.Member;
import kw.zeropick.member.service.MemberService;
import kw.zeropick.product.service.ProductService;
import kw.zeropick.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CorrectionServiceImpl implements CorrectionService {
    private final CorrectionRepository correctionRepository;
    private final MemberService memberService;
    private final ProductService productService;
    private final S3Util s3Util;

    @Override
    @Transactional
    public CorrectionResponse correctionProduct(Long memberId, Long productId, CorrectionRequest request, List<MultipartFile> files) {
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

        Correction correction = Correction.builder()
                .member(memberService.getById(memberId))
                .product(productService.getById(productId))
                .correctionStatus(CorrectionStatus.PENDING)
                .additional(request.getAdditional())
                .imageUrls(images)
                .build();

        Correction saved = correctionRepository.save(correction);

        return CorrectionResponse.builder()
                .id(saved.getId())
                .memberId(saved.getMember().getId())
                .productId(saved.getProduct().getId())
                .productName(saved.getProduct().getProductName())
                .brand(saved.getProduct().getBrand())
                .productImageUrl(saved.getProduct().getImageUrl())
                .imageUrls(saved.getImageUrls())
                .additional(saved.getAdditional())
                .correctionStatus(saved.getCorrectionStatus())
                .rejectionReason(saved.getRejectionReason())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    @Override
    public List<CorrectionResponse> getAllCorrections(Member member) {
        List<Correction> corrections = correctionRepository.findByMember(member);

        List<CorrectionResponse> correctionResponses = new ArrayList<>();
        for (Correction correction : corrections) {
            CorrectionResponse response = CorrectionResponse.builder()
                    .id(correction.getId())
                    .memberId(correction.getMember().getId())
                    .productId(correction.getProduct().getId())
                    .productName(correction.getProduct().getProductName())
                    .brand(correction.getProduct().getBrand())
                    .productImageUrl(correction.getProduct().getImageUrl())
                    .imageUrls(correction.getImageUrls())
                    .additional(correction.getAdditional())
                    .correctionStatus(correction.getCorrectionStatus())
                    .rejectionReason(correction.getRejectionReason())
                    .createdAt(correction.getCreatedAt())
                    .build();
            correctionResponses.add(response);
        }

        return correctionResponses;
    }

    @Override
    @Transactional
    public void deleteCorrection(Long correctionId, Long memberId) {
        Correction correction = correctionRepository.findById(correctionId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 수정 요청 없음 id: " + correctionId));

        if(!correction.getMember().getId().equals(memberId)) {
            throw new EntityNotFoundException("같은 사용자의 수정 요청만 삭제 가능합니다.");
        }

        correctionRepository.delete(correction);
    }
}
