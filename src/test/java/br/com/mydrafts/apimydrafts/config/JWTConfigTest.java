package br.com.mydrafts.apimydrafts.config;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import io.jsonwebtoken.impl.DefaultJwtParserBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JWTConfigTest {

    private final JWTConfig jwtConfig = new JWTConfig();

    @Test
    void shouldReturnJwtBuilder() {
        JwtBuilder jwtBuilder = jwtConfig.jwtBuilder();

        assertThat(jwtBuilder).isExactlyInstanceOf(DefaultJwtBuilder.class);
    }

    @Test
    void shouldReturnJwtParserBuilder() {
        JwtParserBuilder jwtParserBuilder = jwtConfig.jwtParserBuilder();

        assertThat(jwtParserBuilder).isExactlyInstanceOf(DefaultJwtParserBuilder.class);
    }

}
