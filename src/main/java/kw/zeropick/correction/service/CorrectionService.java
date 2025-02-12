package kw.zeropick.correction.service;

import java.util.List;
import kw.zeropick.correction.dto.request.CorrectionRequest;
import kw.zeropick.correction.dto.response.CorrectionResponse;
import kw.zeropick.member.domain.Member;
import kw.zeropick.product.domain.Product;
import org.springframework.web.multipart.MultipartFile;

public interface CorrectionService {
    public CorrectionResponse correctionProduct(Long memberId, Long productId, CorrectionRequest request, List<MultipartFile> files);
    public List<CorrectionResponse> getAllCorrections(Member member);
    public void deleteCorrection(Long correctionId, Long memberId);
}
