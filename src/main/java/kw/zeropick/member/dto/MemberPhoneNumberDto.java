package kw.zeropick.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberPhoneNumberDto {
    @NotNull
    private String phoneNumber;

    @Builder
    public MemberPhoneNumberDto(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}