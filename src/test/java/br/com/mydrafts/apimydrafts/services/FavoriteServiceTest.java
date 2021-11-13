package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.clients.TMDBProxy;
import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Favorite;
import br.com.mydrafts.apimydrafts.dto.FavoriteDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.repository.FavoriteRepository;
import br.com.mydrafts.apimydrafts.repository.ProductionRepository;
import br.com.mydrafts.apimydrafts.repository.UserRepository;
import br.com.mydrafts.apimydrafts.utils.FavoriteUtil;
import br.com.mydrafts.apimydrafts.utils.ProductionUtil;
import br.com.mydrafts.apimydrafts.utils.UserUtil;
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
public class FavoriteServiceTest {

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
    public void saveFavoriteShouldReturnSuccessful() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserUtil.getUser()));
        when(productionRepository.findByTmdbID(any(Integer.class))).thenReturn(Optional.of(ProductionUtil.getProduction(Media.MOVIE)));
        when(favoriteRepository.save(any())).thenReturn(FavoriteUtil.getFavorite(Media.MOVIE));

        FavoriteDTO favoriteDTO = service.save(FavoriteUtil.favoriteForm());

        assertThat(favoriteDTO.getProduction().getId()).isEqualTo(ProductionUtil.getProduction(Media.MOVIE).getId());
        assertThat(favoriteDTO.getUser().getId()).isEqualTo(UserUtil.getUser().getId());
    }

    @Test
    @DisplayName("Service save favorite find movie tmdb")
    public void saveFavoriteFindMovieTMDBShouldReturnSuccessful() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserUtil.getUser()));
        when(productionRepository.findByTmdbID(any(Integer.class))).thenReturn(Optional.empty());
        when(tmdbProxy.findProduction(any(), any())).thenReturn(ProductionUtil.getProduction(Media.MOVIE));
        when(productionRepository.save(any())).thenReturn(ProductionUtil.getProduction(Media.MOVIE));
        when(favoriteRepository.save(any())).thenReturn(FavoriteUtil.getFavorite(Media.MOVIE));

        FavoriteDTO favoriteDTO = service.save(FavoriteUtil.favoriteForm());

        assertThat(favoriteDTO.getProduction().getId()).isEqualTo(ProductionUtil.getProduction(Media.MOVIE).getId());
        assertThat(favoriteDTO.getUser().getId()).isEqualTo(UserUtil.getUser().getId());
    }

    @Test
    @DisplayName("Service save favorite user not found")
    public void saveFavoriteShouldReturnUserNotFound() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(FavoriteUtil.favoriteForm()));
    }

    @Test
    @DisplayName("Service favorite already registered")
    public void saveFavoriteShouldReturnAlreadyRegistered() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserUtil.getUser()));
        when(productionRepository.findByTmdbID(any(Integer.class))).thenReturn(Optional.of(ProductionUtil.getProduction(Media.MOVIE)));
        when(favoriteRepository.existsByUserAndProduction(any(), any())).thenReturn(true);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(FavoriteUtil.favoriteForm()));
    }

    @Test
    @DisplayName("Service delete favorite")
    public void deleteFavoriteShouldReturnSuccessful() {
        when(favoriteRepository.findById(anyString())).thenReturn(Optional.of(FavoriteUtil.getFavorite(Media.MOVIE)));
        doNothing().when(favoriteRepository).delete(any());

        service.delete("1");

        verify(favoriteRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Service delete favorite not found")
    public void deleteFavoriteShouldReturnNotFound() {
        when(favoriteRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.delete("10"));
    }

    private Page<Favorite> pageFavorite() {
        return new PageImpl<>(Arrays.asList(FavoriteUtil.getFavorite(Media.MOVIE)), PageRequest.of(0, 10), 1);
    }

}
