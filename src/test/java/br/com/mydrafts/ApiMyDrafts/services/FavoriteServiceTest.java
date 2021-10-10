package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.clients.TMDBProxy;
import br.com.mydrafts.ApiMyDrafts.constants.Gender;
import br.com.mydrafts.ApiMyDrafts.constants.Media;
import br.com.mydrafts.ApiMyDrafts.documents.Favorite;
import br.com.mydrafts.ApiMyDrafts.documents.Production;
import br.com.mydrafts.ApiMyDrafts.documents.User;
import br.com.mydrafts.ApiMyDrafts.dto.FavoriteDTO;
import br.com.mydrafts.ApiMyDrafts.dto.FavoriteFormDTO;
import br.com.mydrafts.ApiMyDrafts.exceptions.BusinessException;
import br.com.mydrafts.ApiMyDrafts.repository.FavoriteRepository;
import br.com.mydrafts.ApiMyDrafts.repository.ProductionRepository;
import br.com.mydrafts.ApiMyDrafts.repository.UserRepository;
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
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user()));
        when(productionRepository.findByTmdbID(any(Integer.class))).thenReturn(Optional.of(production(Media.movie)));
        when(favoriteRepository.save(any())).thenReturn(favorite(Media.movie));

        FavoriteDTO favoriteDTO = service.save(favoriteForm());

        assertThat(favoriteDTO.getProduction().getId()).isEqualTo(production(Media.movie).getId());
        assertThat(favoriteDTO.getUser().getId()).isEqualTo(user().getId());
    }

    @Test
    @DisplayName("Service save favorite find movie tmdb")
    public void saveFavoriteFindMovieTMDBShouldReturnSuccessful() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user()));
        when(productionRepository.findByTmdbID(any(Integer.class))).thenReturn(Optional.empty());
        when(tmdbProxy.findProduction(any(), any())).thenReturn(production(Media.movie));
        when(productionRepository.save(any())).thenReturn(production(Media.movie));
        when(favoriteRepository.save(any())).thenReturn(favorite(Media.movie));

        FavoriteDTO favoriteDTO = service.save(favoriteForm());

        assertThat(favoriteDTO.getProduction().getId()).isEqualTo(production(Media.movie).getId());
        assertThat(favoriteDTO.getUser().getId()).isEqualTo(user().getId());
    }

    @Test
    @DisplayName("Service save favorite user not found")
    public void saveFavoriteShouldReturnUserNotFound() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(favoriteForm()));
    }

    @Test
    @DisplayName("Service favorite already registered")
    public void saveFavoriteShouldReturnAlreadyRegistered() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user()));
        when(productionRepository.findByTmdbID(any(Integer.class))).thenReturn(Optional.of(production(Media.movie)));
        when(favoriteRepository.existsByUserAndProduction(any(), any())).thenReturn(true);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(favoriteForm()));
    }

    @Test
    @DisplayName("Service get favorites by user")
    public void getFavoritesShouldReturnSuccessful() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user()));
        when(favoriteRepository.findByUser(any(), any())).thenReturn(pageFavorite());

        Page<FavoriteDTO> page = service.getFavorites(PageRequest.of(0, 10), "61586ad5362766670067edd5");

        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getContent().get(0).getProduction().getMedia()).isEqualTo(Media.movie.toString());
    }

    @Test
    @DisplayName("Service get favorites return user not found")
    public void getFavoritesShouldReturnUserNotFound() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.getFavorites(PageRequest.of(0, 10), "61586ad5362766670067edd5"));
    }

    @Test
    @DisplayName("Service delete favorite")
    public void deleteFavoriteShouldReturnSuccessful() {
        when(favoriteRepository.findById(anyString())).thenReturn(Optional.of(favorite(Media.movie)));
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

    private FavoriteFormDTO favoriteForm() {
        return FavoriteFormDTO.builder()
                .media("movie")
                .tmdbID(550988)
                .userID("61586ad5362766670067edd5")
                .build();
    }

    private Favorite favorite(Media media) {
        return Favorite.builder()
                .id("1000")
                .user(user())
                .production(production(media))
                .build();
    }

    private Page<Favorite> pageFavorite() {
        return new PageImpl<>(Arrays.asList(favorite(Media.movie)), PageRequest.of(0, 10), 1);
    }

    private User user() {
        User user = new User();
        user.setId("61586ad5362766670067edd5");
        user.setName("Alvaro");
        user.setGender(Gender.MASCULINO);
        user.setEmail("alvaro@email.com");
        user.setPassword("12345678");
        return user;
    }

    private Production production(Media media) {
        return Production.builder()
                .id("6158fb48b7179927e035ae7b")
                .tmdbID(550988)
                .media(media.toString())
                .build();
    }

}
