package ZeroPick.server.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    @GetMapping("/deploy-check")
    public String checkDeployment() {
        return "배포 성공! Spring Boot 서버가 정상적으로 실행 중입니다.";
    }
}
