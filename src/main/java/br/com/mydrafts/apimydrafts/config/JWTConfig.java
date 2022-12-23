package br.com.mydrafts.apimydrafts.config;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfig {

    @Bean
    public JwtBuilder jwtBuilder() {
        return Jwts.builder();
    }

    @Bean
    public JwtParserBuilder jwtParserBuilder() {
        return Jwts.parserBuilder();
    }

}
