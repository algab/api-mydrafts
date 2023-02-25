package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.fixtures.UserFixture;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for JWTService")
class JWTServiceTest {

    private JWTService jwtService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private JwtParserBuilder jwtParserBuilder;

    @Mock
    private HttpServletRequest httpServletRequest;

    @BeforeEach
    void setup() {
        jwtService = new JWTServiceImpl(Jwts.builder(), jwtParserBuilder, httpServletRequest);
        ReflectionTestUtils.setField(jwtService, "secret", "aMH%Q#LHJL@oCWZko@x)+cYk,r:}kHz@T6rlj4st");
        when(httpServletRequest.getHeader(anyString())).thenReturn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwianRpIjoiMTAiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.MZ-rfQIn1WE3XtXvVxWhgRHIUYmkCnSa45LS92dExn4");
    }

    @Test
    @DisplayName("Service that returns token id")
    void shouldReturnIdToken() {
        when(jwtParserBuilder.setSigningKey(any(SecretKey.class)).build().parseClaimsJws(anyString()).getBody())
            .thenReturn(new DefaultClaims(Map.of("jti", "10")));

        String id = jwtService.getIdByToken();

        assertThat(id).isEqualTo("10");
    }

    @Test
    @DisplayName("Token generation service")
    void shouldReturnTokenGenerateToken() {
        String token = jwtService.generateToken(UserFixture.getUserDTO());

        assertThat(token).isNotEmpty();
    }

    @Test
    @DisplayName("Validation service when token is correct")
    void shouldReturnTrueValidateToken() {
        when(jwtParserBuilder.setSigningKey(any(SecretKey.class)).build().parseClaimsJws(anyString()))
            .thenReturn(new DefaultJws<>(new DefaultJwsHeader(), null, "test"));

        boolean result = jwtService.validateToken();

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Validation service when token is incorrect")
    void shouldReturnExceptionValidateToken() {
        when(jwtParserBuilder.setSigningKey(any(SecretKey.class)).build().parseClaimsJws(anyString()))
            .thenThrow(new JwtException("JWT Error"));

        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> jwtService.validateToken());
    }

}
