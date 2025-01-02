package kw.zeropick.review.dto.response;

import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import kw.zeropick.common.converter.StringListToStringConverter;
import kw.zeropick.review.domain.NegativeTagEnum;
import kw.zeropick.review.domain.PositiveTagEnum;
import kw.zeropick.review.domain.ReviewTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ReviewResponse {
    private Long id;

    private String userName;

    private Long rating;

    private String content;

    private List<String> imageUrls = new ArrayList<>();

    private List<PositiveTagEnum> positiveTags = new ArrayList<>();

    private List<NegativeTagEnum> negativeTags = new ArrayList<>();
}
