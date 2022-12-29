package br.com.mydrafts.apimydrafts.aspects;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.controllers.DraftController;
import br.com.mydrafts.apimydrafts.controllers.FavoriteController;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.fixtures.DraftFixture;
import br.com.mydrafts.apimydrafts.fixtures.FavoriteFixture;
import br.com.mydrafts.apimydrafts.repository.DraftRepository;
import br.com.mydrafts.apimydrafts.repository.FavoriteRepository;
import br.com.mydrafts.apimydrafts.services.JWTService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AuthorizeDataAspectTest {

    private AuthorizeDataAspect authorizeDataAspect;

    @Mock
    private JWTService jwtService;

    @Mock
    private DraftRepository draftRepository;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private JoinPoint joinPoint;

    @Mock
    private MethodSignature methodSignature;

    @BeforeEach
    void setup() {
        authorizeDataAspect = new AuthorizeDataAspect(jwtService, draftRepository, favoriteRepository);
    }

    @Test
    void validateDataDraftShouldReturnSuccessful() throws NoSuchMethodException {
        Method method = DraftController.class.getDeclaredMethod("search", String.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(jwtService.getIdByToken()).thenReturn("61586ad5362766670067edd5");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"61586ad5362766670067eda8"});
        when(draftRepository.findById(anyString())).thenReturn(Optional.of(DraftFixture.getDraft(Media.MOVIE)));

        authorizeDataAspect.authorizeData(joinPoint);

        verify(jwtService, times(1)).getIdByToken();
        verify(draftRepository, times(1)).findById(anyString());
    }

    @Test
    void whenDraftNotFoundShouldReturnException() throws NoSuchMethodException {
        Method method = DraftController.class.getDeclaredMethod("search", String.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(jwtService.getIdByToken()).thenReturn("61586ad5362766670067edd5");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"61586ad5362766670067eda8"});
        when(draftRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> authorizeDataAspect.authorizeData(joinPoint));
    }

    @Test
    void whenUserAndDraftDifferentShouldReturnException() throws NoSuchMethodException {
        Method method = DraftController.class.getDeclaredMethod("search", String.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(jwtService.getIdByToken()).thenReturn("61586ad5362766670067edd8");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"61586ad5362766670067eda8"});
        when(draftRepository.findById(anyString())).thenReturn(Optional.of(DraftFixture.getDraft(Media.MOVIE)));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> authorizeDataAspect.authorizeData(joinPoint));
    }

    @Test
    void validateDataFavoriteShouldReturnSuccessful() throws NoSuchMethodException {
        Method method = FavoriteController.class.getDeclaredMethod("delete", String.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(jwtService.getIdByToken()).thenReturn("61586ad5362766670067edd5");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"61586ad5362766670067eda8"});
        when(favoriteRepository.findById(anyString())).thenReturn(Optional.of(FavoriteFixture.getFavorite(Media.TV)));

        authorizeDataAspect.authorizeData(joinPoint);

        verify(jwtService, times(1)).getIdByToken();
        verify(favoriteRepository, times(1)).findById(anyString());
    }

    @Test
    void whenFavoriteNotFoundShouldReturnException() throws NoSuchMethodException {
        Method method = FavoriteController.class.getDeclaredMethod("delete", String.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(jwtService.getIdByToken()).thenReturn("61586ad5362766670067edd5");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"61586ad5362766670067eda8"});
        when(favoriteRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> authorizeDataAspect.authorizeData(joinPoint));
    }

    @Test
    void whenUserAndFavoriteDifferentShouldReturnException() throws NoSuchMethodException {
        Method method = FavoriteController.class.getDeclaredMethod("delete", String.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(jwtService.getIdByToken()).thenReturn("61586ad5362766670067edd8");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"61586ad5362766670067eda8"});
        when(favoriteRepository.findById(anyString())).thenReturn(Optional.of(FavoriteFixture.getFavorite(Media.TV)));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> authorizeDataAspect.authorizeData(joinPoint));
    }

    @Test
    void whenTheRequestTokenIsNullItShouldReturnException() throws NoSuchMethodException {
        Method method = FavoriteController.class.getDeclaredMethod("delete", String.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(jwtService.getIdByToken()).thenReturn(null);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> authorizeDataAspect.authorizeData(joinPoint));
    }

}
