package kw.zeropick.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(servers = {@io.swagger.v3.oas.annotations.servers.Server(url = "https://ec2-15-164-252-103.ap-northeast-2.compute.amazonaws.com"
        , description = "Default server URL")})

@Configuration
public class Config {

    @Value("${api.server.url}")
    private String serverUrl;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(info)
                .addServersItem(new Server().url(serverUrl));
    }

    Info info = new Info().title("ZEROPICK Backend APIS").version("0.0.1").description(
            "<h3>ZEROPICK Backend APIS</h3>");

}
