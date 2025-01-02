package kw.zeropick.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@OpenAPIDefinition(servers = {@Server(url = "http://ec2-15-164-252-103.ap-northeast-2.compute.amazonaws.com:8080",
//        description = "Default Server URL")})

@Configuration
public class ZeroPickConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000",
                        "http://ec2-15-164-252-103.ap-northeast-2.compute.amazonaws.com:8080", "http://localhost:8080")
                .allowedMethods("GET", "POST", "PATCH", "DELETE", "PUT")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
