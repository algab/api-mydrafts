package br.com.mydrafts.apimydrafts.config;

import br.com.mydrafts.apimydrafts.interceptors.AuthInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for WebConfig")
class WebConfigTest {

    private WebConfig webConfig;

    @Mock
    private AuthInterceptor authInterceptor;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private CorsRegistry corsRegistry;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private InterceptorRegistry interceptorRegistry;

    @BeforeEach
    void setup() {
        webConfig = new WebConfig(authInterceptor);
    }

    @Test
    void whenConfigCorsMustExecuteTheMethodAddMapping() {
        var corsRegistration = Mockito.mock(CorsRegistration.class);
        when(corsRegistry.addMapping(anyString()).allowedMethods(anyString()).allowedOrigins(anyString()))
            .thenReturn(corsRegistration);

        webConfig.addCorsMappings(corsRegistry);

        verify(corsRegistry, times(1)).addMapping("*");
    }

    @Test
    void whenToRegisterInterceptorsMustExecuteMethodAddInterceptor() {
        var interceptorRegistration = Mockito.mock(InterceptorRegistration.class);
        when(interceptorRegistry.addInterceptor(any()).excludePathPatterns(anyString()))
            .thenReturn(interceptorRegistration);

        webConfig.addInterceptors(interceptorRegistry);

        verify(interceptorRegistry, times(1)).addInterceptor(authInterceptor);
    }

}
