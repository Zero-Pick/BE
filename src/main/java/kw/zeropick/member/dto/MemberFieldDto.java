package kw.zeropick.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class MemberFieldDto {
    private List<String> field;

    @Builder
    public MemberFieldDto(List<String> field) {
        this.field = field;
    }
}
