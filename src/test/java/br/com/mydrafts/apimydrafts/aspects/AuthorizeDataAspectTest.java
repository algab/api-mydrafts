package br.com.mydrafts.apimydrafts.aspects;

import br.com.mydrafts.apimydrafts.controllers.DraftController;
import br.com.mydrafts.apimydrafts.controllers.FavoriteController;
import br.com.mydrafts.apimydrafts.documents.ProductionDocument;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.fixtures.DraftFixture;
import br.com.mydrafts.apimydrafts.fixtures.FavoriteFixture;
import br.com.mydrafts.apimydrafts.fixtures.ProductionFixture;
import br.com.mydrafts.apimydrafts.repository.DraftRepository;
import br.com.mydrafts.apimydrafts.repository.FavoriteRepository;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for AuthorizeDataAspect")
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
    @DisplayName("Validate data draft")
    void validateDataDraftShouldReturnSuccessful() throws NoSuchMethodException {
        Method method = DraftController.class.getDeclaredMethod("search", String.class);
        ProductionDocument production = ProductionFixture.getProductionMovie();
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(jwtService.getIdByToken()).thenReturn("61586ad5362766670067edd5");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"61586ad5362766670067eda8"});
        when(draftRepository.findById("61586ad5362766670067eda8")).thenReturn(Optional.of(DraftFixture.getDraft(production)));

        authorizeDataAspect.authorizeData(joinPoint);

        verify(joinPoint, times(1)).getArgs();
        verify(draftRepository, times(1)).findById("61586ad5362766670067eda8");
    }

    @Test
    @DisplayName("Validate the request when draft not found")
    void whenDraftNotFoundShouldReturnException() throws NoSuchMethodException {
        Method method = DraftController.class.getDeclaredMethod("search", String.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(jwtService.getIdByToken()).thenReturn("61586ad5362766670067edd5");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"61586ad5362766670067eda8"});
        when(draftRepository.findById("61586ad5362766670067eda8")).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> authorizeDataAspect.authorizeData(joinPoint));
    }

    @Test
    @DisplayName("Validate the request when the draft is not the correct user")
    void whenUserAndDraftDifferentShouldReturnException() throws NoSuchMethodException {
        ProductionDocument production = ProductionFixture.getProductionMovie();
        Method method = DraftController.class.getDeclaredMethod("search", String.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(jwtService.getIdByToken()).thenReturn("61586ad5362766670067edd8");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"61586ad5362766670067eda8"});
        when(draftRepository.findById("61586ad5362766670067eda8")).thenReturn(Optional.of(DraftFixture.getDraft(production)));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> authorizeDataAspect.authorizeData(joinPoint));
    }

    @Test
    @DisplayName("Validate data favorite")
    void validateDataFavoriteShouldReturnSuccessful() throws NoSuchMethodException {
        Method method = FavoriteController.class.getDeclaredMethod("delete", String.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(jwtService.getIdByToken()).thenReturn("61586ad5362766670067edd5");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"61586ad5362766670067eda8"});
        when(favoriteRepository.findById("61586ad5362766670067eda8")).thenReturn(Optional.of(FavoriteFixture.getFavorite()));

        authorizeDataAspect.authorizeData(joinPoint);

        verify(joinPoint, times(1)).getArgs();
        verify(favoriteRepository, times(1)).findById("61586ad5362766670067eda8");
    }

    @Test
    @DisplayName("Validate the request when favorite not found")
    void whenFavoriteNotFoundShouldReturnException() throws NoSuchMethodException {
        Method method = FavoriteController.class.getDeclaredMethod("delete", String.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(jwtService.getIdByToken()).thenReturn("61586ad5362766670067edd5");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"61586ad5362766670067eda8"});
        when(favoriteRepository.findById("61586ad5362766670067eda8")).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> authorizeDataAspect.authorizeData(joinPoint));
    }

    @Test
    @DisplayName("Validate the request when the favorite is not the correct user")
    void whenUserAndFavoriteDifferentShouldReturnException() throws NoSuchMethodException {
        Method method = FavoriteController.class.getDeclaredMethod("delete", String.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(jwtService.getIdByToken()).thenReturn("61586ad5362766670067edd8");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"61586ad5362766670067eda8"});
        when(favoriteRepository.findById("61586ad5362766670067eda8")).thenReturn(Optional.of(FavoriteFixture.getFavorite()));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> authorizeDataAspect.authorizeData(joinPoint));
    }

    @Test
    @DisplayName("Validate the request when the token is null")
    void whenTheRequestTokenIsNullItShouldReturnException() throws NoSuchMethodException {
        Method method = FavoriteController.class.getDeclaredMethod("delete", String.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(jwtService.getIdByToken()).thenReturn(null);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> authorizeDataAspect.authorizeData(joinPoint));
    }

}
