package kw.zeropick.member.controller.response;

import lombok.Data;

@Data
public class CreateMemberResponse {
    private Long id;
    private String message;

    public CreateMemberResponse(String message) {
        this.message = message;
    }
    public CreateMemberResponse(Long id, String message){
        this.id = id;
        this.message = message;
    }

}
