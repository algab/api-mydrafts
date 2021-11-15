package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.clients.TMDBProxy;
import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Draft;
import br.com.mydrafts.apimydrafts.dto.DraftDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.repository.DraftRepository;
import br.com.mydrafts.apimydrafts.repository.ProductionRepository;
import br.com.mydrafts.apimydrafts.repository.UserRepository;
import br.com.mydrafts.apimydrafts.utils.DraftUtil;
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
@DisplayName("Tests for Draft Service")
class DraftServiceTest {

    @Autowired
    private DraftService service;

    @MockBean
    private DraftRepository draftRepository;

    @MockBean
    private ProductionRepository productionRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TMDBProxy tmdbProxy;

    @Test
    @DisplayName("Service save draft")
    void saveDraftShouldReturnSuccessful() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserUtil.getUser()));
        when(productionRepository.findByTmdbID(any(Integer.class))).thenReturn(Optional.of(ProductionUtil.getProduction(Media.MOVIE)));
        when(draftRepository.save(any(Draft.class))).thenReturn(DraftUtil.getDraft(Media.MOVIE));

        DraftDTO draftDTO = service.save(DraftUtil.draftForm(Media.MOVIE));

        assertThat(draftDTO.getDescription()).isEqualTo(DraftUtil.getDraft(Media.MOVIE).getDescription());
        assertThat(draftDTO.getUser().getId()).isEqualTo(DraftUtil.getDraft(Media.MOVIE).getUser().getId());
        assertThat(draftDTO.getProduction().getId()).isEqualTo(DraftUtil.getDraft(Media.MOVIE).getProduction().getId());
    }

    @Test
    @DisplayName("Service save draft find movie tmdb")
    void saveDraftFindMovieTMDBShouldReturnSuccessful() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserUtil.getUser()));
        when(productionRepository.findByTmdbID(any(Integer.class))).thenReturn(Optional.empty());
        when(tmdbProxy.findProduction(any(), any())).thenReturn(ProductionUtil.getProduction(Media.MOVIE));
        when(productionRepository.save(any())).thenReturn(ProductionUtil.getProduction(Media.MOVIE));
        when(draftRepository.save(any(Draft.class))).thenReturn(DraftUtil.getDraft(Media.MOVIE));

        DraftDTO draftDTO = service.save(DraftUtil.draftForm(Media.MOVIE));

        assertThat(draftDTO.getDescription()).isEqualTo(DraftUtil.getDraft(Media.MOVIE).getDescription());
        assertThat(draftDTO.getUser().getId()).isEqualTo(DraftUtil.getDraft(Media.MOVIE).getUser().getId());
        assertThat(draftDTO.getProduction().getId()).isEqualTo(DraftUtil.getDraft(Media.MOVIE).getProduction().getId());
    }

    @Test
    @DisplayName("Service save draft find tv tmdb")
    void saveDraftFindTVTMDBShouldReturnSuccessful() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserUtil.getUser()));
        when(productionRepository.findByTmdbID(any(Integer.class))).thenReturn(Optional.empty());
        when(tmdbProxy.findProduction(any(), any())).thenReturn(ProductionUtil.getProduction(Media.TV));
        when(productionRepository.save(any())).thenReturn(ProductionUtil.getProduction(Media.TV));
        when(draftRepository.save(any(Draft.class))).thenReturn(DraftUtil.getDraft(Media.TV));

        DraftDTO draftDTO = service.save(DraftUtil.draftForm(Media.TV));

        assertThat(draftDTO.getProduction().getMedia()).isEqualTo(Media.TV);
        assertThat(draftDTO.getDescription()).isEqualTo(DraftUtil.getDraft(Media.MOVIE).getDescription());
        assertThat(draftDTO.getUser().getId()).isEqualTo(DraftUtil.getDraft(Media.MOVIE).getUser().getId());
        assertThat(draftDTO.getProduction().getId()).isEqualTo(DraftUtil.getDraft(Media.MOVIE).getProduction().getId());
    }

    @Test
    @DisplayName("Service draft already registered")
    void saveDraftShouldReturnAlreadyRegistered() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserUtil.getUser()));
        when(productionRepository.findByTmdbID(any(Integer.class))).thenReturn(Optional.of(ProductionUtil.getProduction(Media.MOVIE)));
        when(draftRepository.existsByUserAndProduction(any(), any())).thenReturn(true);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(DraftUtil.draftForm(Media.TV)));
    }

    @Test
    @DisplayName("Service save draft user not found")
    void saveDraftShouldReturnUserNotFound() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(DraftUtil.draftForm(Media.TV)));
    }

    @Test
    @DisplayName("Service search draft by id")
    void searchDraftShouldReturnSuccessful() {
        when(draftRepository.findById(anyString())).thenReturn(Optional.of(DraftUtil.getDraft(Media.MOVIE)));

        DraftDTO draftDTO = service.searchDraft("61586ad5362766670067eda8");

        assertThat(draftDTO.getProduction().getMedia()).isEqualTo(Media.MOVIE);
        assertThat(draftDTO.getRating()).isEqualTo(DraftUtil.getDraft(Media.MOVIE).getRating());
        assertThat(draftDTO.getDescription()).isEqualTo(DraftUtil.getDraft(Media.MOVIE).getDescription());
    }

    @Test
    @DisplayName("Service search draft not found")
    void searchDraftShouldReturnNotFound() {
        when(draftRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.searchDraft("61586ad5362766670067eda8"));
    }

    @Test
    @DisplayName("Service update draft")
    void updateDraftShouldReturnSuccessful() {
        when(draftRepository.findById(anyString())).thenReturn(Optional.of(DraftUtil.getDraft(Media.MOVIE)));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserUtil.getUser()));
        when(productionRepository.findByTmdbID(any(Integer.class))).thenReturn(Optional.of(ProductionUtil.getProduction(Media.MOVIE)));
        when(draftRepository.save(any(Draft.class))).thenReturn(DraftUtil.getDraft(Media.MOVIE));

        DraftDTO draftDTO = service.updateDraft("61586ad5362766670067eda8", DraftUtil.draftForm(Media.MOVIE));

        assertThat(draftDTO.getDescription()).isEqualTo(DraftUtil.getDraft(Media.MOVIE).getDescription());
        assertThat(draftDTO.getUser().getId()).isEqualTo(DraftUtil.getDraft(Media.MOVIE).getUser().getId());
        assertThat(draftDTO.getProduction().getId()).isEqualTo(DraftUtil.getDraft(Media.MOVIE).getProduction().getId());
    }

    @Test
    @DisplayName("Service update draft not found")
    void updateDraftShouldReturnDraftNotFound() {
        when(draftRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.updateDraft("61586ad5362766670067eda8", DraftUtil.draftForm(Media.MOVIE)));
    }

    @Test
    @DisplayName("Service update draft return user not found")
    void updateDraftShouldReturnUserNotFound() {
        when(draftRepository.findById(anyString())).thenReturn(Optional.of(DraftUtil.getDraft(Media.MOVIE)));
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.updateDraft("61586ad5362766670067eda8", DraftUtil.draftForm(Media.MOVIE)));
    }

    @Test
    @DisplayName("Service delete draft")
    void deleteDraftShouldReturnSuccessful() {
        when(draftRepository.findById(anyString())).thenReturn(Optional.of(DraftUtil.getDraft(Media.MOVIE)));
        doNothing().when(draftRepository).delete(any());

        service.deleteDraft("61586ad5362766670067eda8");

        verify(draftRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Service delete draft not found")
    void deleteDraftShouldReturnNotFound() {
        when(draftRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.deleteDraft("61586ad5362766670067eda8"));
    }

    private Page<Draft> pageDraft() {
        return new PageImpl<>(Arrays.asList(DraftUtil.getDraft(Media.MOVIE)), PageRequest.of(0, 10), 1);
    }

}
