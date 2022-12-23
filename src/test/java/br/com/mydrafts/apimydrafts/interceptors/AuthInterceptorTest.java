package br.com.mydrafts.apimydrafts.interceptors;

import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.services.JWTService;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for AuthInterceptor")
class AuthInterceptorTest {

    private AuthInterceptor authInterceptor;

    @Mock
    private JWTService jwtService;

    @BeforeEach
    void setup() {
        authInterceptor = new AuthInterceptor(jwtService);
    }

    @Test
    void whenTheTokenSentHasTheCorrectSignatureItShouldReturnTrue() {
        when(jwtService.validateToken()).thenReturn(true);
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

        var result = authInterceptor.preHandle(httpServletRequest, httpServletResponse, new Object());

        assertThat(result).isTrue();
    }

    @Test
    void whenRequestIsInTheListItShouldReturnTrue() {
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.setMethod("POST");
        httpServletRequest.setRequestURI("/v1/login");

        var result = authInterceptor.preHandle(httpServletRequest, httpServletResponse, new Object());

        assertThat(result).isTrue();
    }

    @Test
    void whenTheTokenSentHasTheWrongSignatureItShouldReturnAnException() {
        when(jwtService.validateToken()).thenThrow(new JwtException("JWT Error"));
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

        var obj = new Object();

        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> authInterceptor.preHandle(httpServletRequest, httpServletResponse, obj));
    }

    @Test
    void whenNotSendingTheTokenItShouldThrowAnException() {
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();

        var obj = new Object();

        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> authInterceptor.preHandle(httpServletRequest, httpServletResponse, obj));
    }

}
