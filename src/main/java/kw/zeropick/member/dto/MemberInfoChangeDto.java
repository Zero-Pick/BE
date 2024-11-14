package kw.zeropick.member.dto;


import jakarta.validation.constraints.NotNull;
import kw.zeropick.member.domain.MarketingAgree;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MemberInfoChangeDto {
    @NotNull
    private String phoneNumber;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private MarketingAgree marketingAgree;

    @Builder
    public MemberInfoChangeDto(String phoneNumber, LocalDate birthDate, MarketingAgree marketingAgree) {
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.marketingAgree = marketingAgree;
    }
}
