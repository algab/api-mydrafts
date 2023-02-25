package br.com.mydrafts.apimydrafts.config;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import io.jsonwebtoken.impl.DefaultJwtParserBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for JWTConfig")
class JWTConfigTest {

    private final JWTConfig jwtConfig = new JWTConfig();

    @Test
    @DisplayName("Test bean JwtBuilder")
    void shouldReturnJwtBuilder() {
        JwtBuilder jwtBuilder = jwtConfig.jwtBuilder();

        assertThat(jwtBuilder).isExactlyInstanceOf(DefaultJwtBuilder.class);
    }

    @Test
    @DisplayName("Test bean JwtParserBuilder")
    void shouldReturnJwtParserBuilder() {
        JwtParserBuilder jwtParserBuilder = jwtConfig.jwtParserBuilder();

        assertThat(jwtParserBuilder).isExactlyInstanceOf(DefaultJwtParserBuilder.class);
    }

}
