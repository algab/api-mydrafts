package br.com.mydrafts.apimydrafts.aspects;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.controllers.DraftController;
import br.com.mydrafts.apimydrafts.controllers.FavoriteController;
import br.com.mydrafts.apimydrafts.dto.DraftFormDTO;
import br.com.mydrafts.apimydrafts.dto.FavoriteFormDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.services.JWTService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Method;

import static br.com.mydrafts.apimydrafts.fixtures.DraftFixture.getDraftForm;
import static br.com.mydrafts.apimydrafts.fixtures.FavoriteFixture.getFavoriteForm;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for AuthorizeBodyAspect")
class AuthorizeBodyAspectTest {

    private AuthorizeBodyAspect authorizeBodyAspect;

    @Mock
    private JWTService jwtService;

    @Mock
    private JoinPoint joinPoint;

    @Mock
    private MethodSignature methodSignature;

    @BeforeEach
    void setup() {
        authorizeBodyAspect = new AuthorizeBodyAspect(jwtService);
    }

    @Test
    @DisplayName("Validate body draft")
    void validateBodyDraftShouldReturnSuccessful() throws NoSuchMethodException {
        Method method = DraftController.class.getDeclaredMethod("save", DraftFormDTO.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(jwtService.getIdByToken()).thenReturn("61586ad5362766670067edd5");
        when(joinPoint.getArgs()).thenReturn(new Object[] {getDraftForm(550988, Media.MOVIE, null)});

        authorizeBodyAspect.authorizeBody(joinPoint);

        verify(joinPoint, times(1)).getArgs();
        verify(jwtService, times(1)).getIdByToken();
    }

    @Test
    @DisplayName("Validate body favorite")
    void validateBodyFavoriteShouldReturnSuccessful() throws NoSuchMethodException {
        Method method = FavoriteController.class.getDeclaredMethod("save", FavoriteFormDTO.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(jwtService.getIdByToken()).thenReturn("61586ad5362766670067edd5");
        when(joinPoint.getArgs()).thenReturn(new Object[] {getFavoriteForm()});

        authorizeBodyAspect.authorizeBody(joinPoint);

        verify(joinPoint, times(1)).getArgs();
        verify(jwtService, times(1)).getIdByToken();
    }

    @Test
    @DisplayName("Validate request when body invalid")
    void whenBodyIsInvalidShouldReturnException() throws NoSuchMethodException {
        Method method = FavoriteController.class.getDeclaredMethod("save", FavoriteFormDTO.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(jwtService.getIdByToken()).thenReturn("10");
        when(joinPoint.getArgs()).thenReturn(new Object[] {getFavoriteForm()});

        assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> authorizeBodyAspect.authorizeBody(joinPoint));
    }

    @Test
    @DisplayName("Validate the request when the token is null")
    void whenTheRequestTokenIsNullItShouldReturnException() throws NoSuchMethodException {
        Method method = FavoriteController.class.getDeclaredMethod("save", FavoriteFormDTO.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(jwtService.getIdByToken()).thenReturn(null);

        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> authorizeBodyAspect.authorizeBody(joinPoint));
    }

}
