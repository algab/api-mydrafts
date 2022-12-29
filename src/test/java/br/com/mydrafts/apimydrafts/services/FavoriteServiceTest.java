package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.dto.FavoriteDTO;
import br.com.mydrafts.apimydrafts.dto.FavoriteFormDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.repository.FavoriteRepository;
import br.com.mydrafts.apimydrafts.repository.UserRepository;
import br.com.mydrafts.apimydrafts.fixtures.FavoriteFixture;
import br.com.mydrafts.apimydrafts.fixtures.ProductionFixture;
import br.com.mydrafts.apimydrafts.fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Favorite Service")
class FavoriteServiceTest {

    private FavoriteService service;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductionService productionService;

    @BeforeEach
    void setup() {
        service = new FavoriteServiceImpl(favoriteRepository, userRepository, productionService, new ModelMapper());
    }

    @Test
    @DisplayName("Service save favorite")
    void saveFavoriteShouldReturnSuccessful() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserFixture.getUser()));
        when(productionService.searchByTmdbID(any())).thenReturn(ProductionFixture.getProduction(Media.MOVIE));
        when(favoriteRepository.save(any())).thenReturn(FavoriteFixture.getFavorite(Media.MOVIE));

        FavoriteDTO favoriteDTO = service.save(FavoriteFixture.getFavoriteForm());

        assertThat(favoriteDTO.getProduction().getId()).isEqualTo(ProductionFixture.getProduction(Media.MOVIE).getId());
    }

    @Test
    @DisplayName("Service save favorite, production not exists in database")
    void saveFavoriteProductionNotExistsShouldReturnSuccessful() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserFixture.getUser()));
        when(productionService.searchByTmdbID(any())).thenReturn(null);
        when(favoriteRepository.save(any())).thenReturn(FavoriteFixture.getFavorite(Media.MOVIE));

        FavoriteDTO favoriteDTO = service.save(FavoriteFixture.getFavoriteForm());

        assertThat(favoriteDTO.getProduction().getId()).isEqualTo(ProductionFixture.getProduction(Media.MOVIE).getId());
    }

    @Test
    @DisplayName("Service favorite already registered")
    void saveFavoriteShouldReturnAlreadyRegistered() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserFixture.getUser()));
        when(productionService.searchByTmdbID(any())).thenReturn(ProductionFixture.getProduction(Media.MOVIE));
        when(favoriteRepository.existsByUserAndProduction(any(), any())).thenReturn(true);

        FavoriteFormDTO form = FavoriteFixture.getFavoriteForm();

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(form));
    }

    @Test
    @DisplayName("Service save favorite user not found")
    void saveFavoriteShouldReturnUserNotFound() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        FavoriteFormDTO form = FavoriteFixture.getFavoriteForm();

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(form));
    }

    @Test
    @DisplayName("Service delete favorite")
    void deleteFavoriteShouldReturnSuccessful() {
        when(favoriteRepository.findById(anyString())).thenReturn(Optional.of(FavoriteFixture.getFavorite(Media.MOVIE)));
        doNothing().when(favoriteRepository).delete(any());

        service.delete("1");

        verify(favoriteRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Service delete favorite not found")
    void deleteFavoriteShouldReturnNotFound() {
        when(favoriteRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.delete("10"));
    }

}
