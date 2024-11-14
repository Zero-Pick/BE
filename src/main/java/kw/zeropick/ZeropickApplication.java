package kw.zeropick;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = {@Server(url = "https://ec2-15-164-252-103.ap-northeast-2.compute.amazonaws.com"
		, description = "Default server URL")})

@SpringBootApplication
public class ZeropickApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZeropickApplication.class, args);
	}

}
