package br.com.mydrafts.apimydrafts.aspects;

import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.services.JWTService;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for AuthorizeParamAspect")
class AuthorizeParamAspectTest {

    private AuthorizeParamAspect authorizeParamAspect;

    @Mock
    private JWTService jwtService;

    @Mock
    private JoinPoint joinPoint;

    @BeforeEach
    void setup() {
        authorizeParamAspect = new AuthorizeParamAspect(jwtService);
    }

    @Test
    @DisplayName("Validate request parameter")
    void validateParamShouldReturnSuccessful() {
        when(jwtService.getIdByToken()).thenReturn("61586ad5362766670067edd5");
        when(joinPoint.getArgs()).thenReturn(new Object[] {"61586ad5362766670067edd5"});

        authorizeParamAspect.authorizeParam(joinPoint);

        verify(joinPoint, times(1)).getArgs();
        verify(jwtService, times(1)).getIdByToken();
    }

    @Test
    @DisplayName("Validate request parameter different")
    void whenTheValuesIsDifferentItShouldReturnException() {
        when(jwtService.getIdByToken()).thenReturn("61586ad5362766670067edd5");
        when(joinPoint.getArgs()).thenReturn(new Object[] {"61586ad5362766670067edd6"});

        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> authorizeParamAspect.authorizeParam(joinPoint));
    }

    @Test
    @DisplayName("Validate the request when the token is null")
    void whenTheRequestTokenIsNullItShouldReturnException() {
        when(jwtService.getIdByToken()).thenReturn(null);
        when(joinPoint.getArgs()).thenReturn(new Object[] {"61586ad5362766670067edd5"});

        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> authorizeParamAspect.authorizeParam(joinPoint));
    }

}
