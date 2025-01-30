package kw.zeropick.member.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberEmailDto {
    @Email
    private String email;

    @Builder
    public MemberEmailDto(String email) {
        this.email = email;
    }
}