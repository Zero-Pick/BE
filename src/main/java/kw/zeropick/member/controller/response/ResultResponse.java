package kw.zeropick.member.controller.response;


import lombok.Data;

@Data
public class ResultResponse{
    private String message;

    public ResultResponse(){
    }

    public ResultResponse(String message) {
        this.message = message;
    }
}
