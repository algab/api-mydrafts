package br.com.mydrafts.apimydrafts.interceptors;

import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for AuthInterceptor")
class AuthInterceptorTest {

    private AuthInterceptor authInterceptor;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private JwtParserBuilder jwt;

    @BeforeEach
    void setup() {
        authInterceptor = new AuthInterceptor(jwt);
    }

    @Test
    void whenTheTokenSentHasTheCorrectSignatureItShouldReturnTrue() {
        Mockito.when(jwt.setSigningKey(any(SecretKey.class)).build().parseClaimsJws(anyString()))
            .thenReturn(new DefaultJws<>(new DefaultJwsHeader(), new DefaultClaims(), ""));
        ReflectionTestUtils.setField(authInterceptor, "secret", "aMH%Q#LHJL@oCWZko@x)+cYk,r:}kHz@T6rlj4st");
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

        var result = authInterceptor.preHandle(httpServletRequest, httpServletResponse, new Object());

        assertThat(result).isTrue();
    }

    @Test
    void whenTheTokenSentHasTheWrongSignatureItShouldReturnAnException() {
        Mockito.when(jwt.setSigningKey(any(SecretKey.class)).build().parseClaimsJws(anyString()))
            .thenThrow(new JwtException("JWT Error"));
        ReflectionTestUtils.setField(authInterceptor, "secret", "aMH%Q#LHJL@oCWZko@x)+cYk,r:}kHz@T6rlj4st");
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

        var obj = new Object();

        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> authInterceptor.preHandle(httpServletRequest, httpServletResponse, obj));
    }

    @Test
    void whenTheSentTokenIsBadlyFormattedShouldThrowAnException() {
        ReflectionTestUtils.setField(authInterceptor, "secret", "test");
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

        var obj = new Object();

        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> authInterceptor.preHandle(httpServletRequest, httpServletResponse, obj));
    }

    @Test
    void whenNotSendingTheTokenItShouldThrowAnException() {
        ReflectionTestUtils.setField(authInterceptor, "secret", "aMH%Q#LHJL@oCWZko@x)+cYk,r:}kHz@T6rlj4st");
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();

        var obj = new Object();

        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> authInterceptor.preHandle(httpServletRequest, httpServletResponse, obj));
    }

}
