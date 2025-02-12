package kw.zeropick.correction.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CorrectionRequest {
    private Long productId;
    private String additional;
}
