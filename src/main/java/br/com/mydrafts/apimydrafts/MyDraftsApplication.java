package br.com.mydrafts.apimydrafts;

import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@SpringBootApplication
public class MyDraftsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyDraftsApplication.class, args);
	}

	@Bean
	public JwtParserBuilder jwt() {
		return Jwts.parserBuilder();
	}

}
