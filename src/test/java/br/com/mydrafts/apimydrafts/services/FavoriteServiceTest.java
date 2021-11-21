package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.clients.TMDBProxy;
import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Favorite;
import br.com.mydrafts.apimydrafts.dto.FavoriteDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.repository.FavoriteRepository;
import br.com.mydrafts.apimydrafts.repository.ProductionRepository;
import br.com.mydrafts.apimydrafts.repository.UserRepository;
import br.com.mydrafts.apimydrafts.builder.FavoriteBuilder;
import br.com.mydrafts.apimydrafts.builder.ProductionBuilder;
import br.com.mydrafts.apimydrafts.builder.UserBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Favorite Service")
class FavoriteServiceTest {

    @Autowired
    private FavoriteService service;

    @MockBean
    private FavoriteRepository favoriteRepository;

    @MockBean
    private ProductionRepository productionRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TMDBProxy tmdbProxy;

    @Test
    @DisplayName("Service save favorite")
    void saveFavoriteShouldReturnSuccessful() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserBuilder.getUser()));
        when(productionRepository.findByTmdbID(any(Integer.class))).thenReturn(Optional.of(ProductionBuilder.getProduction(Media.MOVIE)));
        when(favoriteRepository.save(any())).thenReturn(FavoriteBuilder.getFavorite(Media.MOVIE));

        FavoriteDTO favoriteDTO = service.save(FavoriteBuilder.favoriteForm());

        assertThat(favoriteDTO.getProduction().getId()).isEqualTo(ProductionBuilder.getProduction(Media.MOVIE).getId());
        assertThat(favoriteDTO.getUser().getId()).isEqualTo(UserBuilder.getUser().getId());
    }

    @Test
    @DisplayName("Service save favorite find movie tmdb")
    void saveFavoriteFindMovieTMDBShouldReturnSuccessful() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserBuilder.getUser()));
        when(productionRepository.findByTmdbID(any(Integer.class))).thenReturn(Optional.empty());
        when(tmdbProxy.findProduction(any(), any())).thenReturn(ProductionBuilder.getProduction(Media.MOVIE));
        when(productionRepository.save(any())).thenReturn(ProductionBuilder.getProduction(Media.MOVIE));
        when(favoriteRepository.save(any())).thenReturn(FavoriteBuilder.getFavorite(Media.MOVIE));

        FavoriteDTO favoriteDTO = service.save(FavoriteBuilder.favoriteForm());

        assertThat(favoriteDTO.getProduction().getId()).isEqualTo(ProductionBuilder.getProduction(Media.MOVIE).getId());
        assertThat(favoriteDTO.getUser().getId()).isEqualTo(UserBuilder.getUser().getId());
    }

    @Test
    @DisplayName("Service save favorite user not found")
    void saveFavoriteShouldReturnUserNotFound() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(FavoriteBuilder.favoriteForm()));
    }

    @Test
    @DisplayName("Service favorite already registered")
    void saveFavoriteShouldReturnAlreadyRegistered() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserBuilder.getUser()));
        when(productionRepository.findByTmdbID(any(Integer.class))).thenReturn(Optional.of(ProductionBuilder.getProduction(Media.MOVIE)));
        when(favoriteRepository.existsByUserAndProduction(any(), any())).thenReturn(true);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(FavoriteBuilder.favoriteForm()));
    }

    @Test
    @DisplayName("Service delete favorite")
    void deleteFavoriteShouldReturnSuccessful() {
        when(favoriteRepository.findById(anyString())).thenReturn(Optional.of(FavoriteBuilder.getFavorite(Media.MOVIE)));
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

    private Page<Favorite> pageFavorite() {
        return new PageImpl<>(Arrays.asList(FavoriteBuilder.getFavorite(Media.MOVIE)), PageRequest.of(0, 10), 1);
    }

}
