package kw.zeropick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ZeropickApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZeropickApplication.class, args);
	}

}

//test