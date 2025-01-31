package kw.zeropick.member.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
public class MypageInfoResponse {
    private String nickname;
    private Long bookmarkCount;
    private Long reviewCount;

    @Builder
    public MypageInfoResponse(String nickname, Long bookmarkCount, Long reviewCount) {
        this.nickname = nickname;
        this.bookmarkCount = bookmarkCount;
        this.reviewCount = reviewCount;
    }
}
