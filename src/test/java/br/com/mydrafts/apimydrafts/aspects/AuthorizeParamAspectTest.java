package br.com.mydrafts.apimydrafts.aspects;

import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.services.JWTService;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
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
    void validateParamShouldReturnSuccessful() {
        when(jwtService.getIdByToken()).thenReturn("61586ad5362766670067edd5");
        when(joinPoint.getArgs()).thenReturn(new Object[] {"61586ad5362766670067edd5"});

        authorizeParamAspect.authorizeParam(joinPoint);

        verify(jwtService, times(1)).getIdByToken();
    }

    @Test
    void whenTheValuesIsDifferentItShouldReturnException() {
        when(jwtService.getIdByToken()).thenReturn("61586ad5362766670067edd5");
        when(joinPoint.getArgs()).thenReturn(new Object[] {"61586ad5362766670067edd6"});

        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> authorizeParamAspect.authorizeParam(joinPoint));
    }

    @Test
    void whenTheRequestTokenIsNullItShouldReturnException() throws NoSuchMethodException {
        when(jwtService.getIdByToken()).thenReturn(null);
        when(joinPoint.getArgs()).thenReturn(new Object[] {"61586ad5362766670067edd5"});

        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> authorizeParamAspect.authorizeParam(joinPoint));
    }

}
