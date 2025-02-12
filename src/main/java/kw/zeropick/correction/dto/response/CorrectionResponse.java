package kw.zeropick.correction.dto.response;


import jakarta.persistence.Convert;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kw.zeropick.common.converter.StringListToStringConverter;
import kw.zeropick.correction.domain.CorrectionStatus;
import kw.zeropick.member.domain.Member;
import kw.zeropick.product.domain.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CorrectionResponse {
    private Long id;

    private Long memberId;

    private Long productId;

    private String productName;

    private String brand;

    private String productImageUrl;

    private List<String> imageUrls;

    private String additional;

    private CorrectionStatus correctionStatus; // 등록 요청 처리 상태

    private String rejectionReason; // 반려 사유

    private LocalDateTime createdAt;
}
