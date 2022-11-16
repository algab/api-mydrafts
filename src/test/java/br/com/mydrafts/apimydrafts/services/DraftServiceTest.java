package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Draft;
import br.com.mydrafts.apimydrafts.dto.DraftDTO;
import br.com.mydrafts.apimydrafts.dto.DraftFormDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.repository.DraftRepository;
import br.com.mydrafts.apimydrafts.repository.UserRepository;
import br.com.mydrafts.apimydrafts.builder.DraftBuilder;
import br.com.mydrafts.apimydrafts.builder.ProductionBuilder;
import br.com.mydrafts.apimydrafts.builder.UserBuilder;
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
@DisplayName("Tests for Draft Service")
class DraftServiceTest {

    private DraftService service;

    @Mock
    private DraftRepository draftRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductionService productionService;

    @BeforeEach
    void setup() {
        service = new DraftServiceImpl(draftRepository, userRepository, productionService, new ModelMapper());
    }

    @Test
    @DisplayName("Service save draft")
    void saveDraftShouldReturnSuccessful() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserBuilder.getUser()));
        when(productionService.searchByTmdbID(any(Integer.class))).thenReturn(ProductionBuilder.getProduction(Media.MOVIE));
        when(draftRepository.existsByUserAndProduction(any(), any())).thenReturn(false);
        when(draftRepository.save(any())).thenReturn(DraftBuilder.getDraft(Media.MOVIE));

        DraftDTO draftDTO = service.save(DraftBuilder.draftForm(Media.MOVIE));

        assertThat(draftDTO.getDescription()).isEqualTo(DraftBuilder.getDraft(Media.MOVIE).getDescription());
        assertThat(draftDTO.getProduction().getId()).isEqualTo(DraftBuilder.getDraft(Media.MOVIE).getProduction().getId());
    }

    @Test
    @DisplayName("Service save draft production movie")
    void saveDraftProductionMovieShouldReturnSuccessful() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserBuilder.getUser()));
        when(productionService.searchByTmdbID(any(Integer.class))).thenReturn(null);
        when(productionService.mountProduction(any(), any(), any())).thenReturn(ProductionBuilder.getProduction(Media.MOVIE));
        when(draftRepository.save(any())).thenReturn(DraftBuilder.getDraft(Media.MOVIE));

        DraftDTO draftDTO = service.save(DraftBuilder.draftForm(Media.MOVIE));

        assertThat(draftDTO.getDescription()).isEqualTo(DraftBuilder.getDraft(Media.MOVIE).getDescription());
        assertThat(draftDTO.getProduction().getId()).isEqualTo(DraftBuilder.getDraft(Media.MOVIE).getProduction().getId());
    }

    @Test
    @DisplayName("Service save draft production tv with season")
    void saveDraftProductionTVShouldReturnSuccessful() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserBuilder.getUser()));
        when(productionService.searchByTmdbIdAndSeason(any(Integer.class), any(Integer.class))).thenReturn(null);
        when(productionService.mountProduction(any(), any(), any())).thenReturn(ProductionBuilder.getProduction(Media.TV));
        when(draftRepository.save(any())).thenReturn(DraftBuilder.getDraft(Media.TV));

        DraftFormDTO form = DraftBuilder.draftForm(Media.TV);
        form.setSeason(1);
        DraftDTO draftDTO = service.save(form);

        assertThat(DraftBuilder.getDraft(Media.TV).getProduction().getMedia()).isEqualTo(draftDTO.getProduction().getMedia());
    }

    @Test
    @DisplayName("Service save draft production tv without season")
    void saveDraftProductionTVShouldReturnBadRequest() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserBuilder.getUser()));
        when(productionService.searchByTmdbIdAndSeason(any(Integer.class), any(Integer.class))).thenReturn(null);

        DraftFormDTO form = DraftBuilder.draftForm(Media.TV);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(form));
    }

    @Test
    @DisplayName("Service draft already registered")
    void saveDraftShouldReturnAlreadyRegistered() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserBuilder.getUser()));
        when(productionService.searchByTmdbID(any(Integer.class))).thenReturn(ProductionBuilder.getProduction(Media.MOVIE));
        when(draftRepository.existsByUserAndProduction(any(), any())).thenReturn(true);

        DraftFormDTO form = DraftBuilder.draftForm(Media.MOVIE);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(form));
    }

    @Test
    @DisplayName("Service save draft user not found")
    void saveDraftShouldReturnUserNotFound() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        DraftFormDTO form = DraftBuilder.draftForm(Media.TV);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(form));
    }

    @Test
    @DisplayName("Service search draft by id")
    void searchDraftShouldReturnSuccessful() {
        when(draftRepository.findById(anyString())).thenReturn(Optional.of(DraftBuilder.getDraft(Media.MOVIE)));

        DraftDTO draftDTO = service.searchDraft("61586ad5362766670067eda8");

        assertThat(draftDTO.getProduction().getMedia()).isEqualTo(Media.MOVIE);
        assertThat(draftDTO.getRating()).isEqualTo(DraftBuilder.getDraft(Media.MOVIE).getRating());
        assertThat(draftDTO.getDescription()).isEqualTo(DraftBuilder.getDraft(Media.MOVIE).getDescription());
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
        when(draftRepository.findById(anyString())).thenReturn(Optional.of(DraftBuilder.getDraft(Media.MOVIE)));
        when(userRepository.existsById(anyString())).thenReturn(true);
        when(draftRepository.findById(anyString())).thenReturn(Optional.of(DraftBuilder.getDraft(Media.MOVIE)));
        when(draftRepository.save(any(Draft.class))).thenReturn(DraftBuilder.getDraft(Media.MOVIE));

        DraftDTO draftDTO = service.updateDraft("61586ad5362766670067eda8", DraftBuilder.draftForm(Media.MOVIE));

        assertThat(draftDTO.getDescription()).isEqualTo(DraftBuilder.getDraft(Media.MOVIE).getDescription());
        assertThat(draftDTO.getProduction().getId()).isEqualTo(DraftBuilder.getDraft(Media.MOVIE).getProduction().getId());
    }

    @Test
    @DisplayName("Service update draft not found")
    void updateDraftShouldReturnDraftNotFound() {
        when(draftRepository.findById(anyString())).thenReturn(Optional.empty());

        DraftFormDTO form = DraftBuilder.draftForm(Media.MOVIE);

        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> service.updateDraft("61586ad5362766670067eda8", form));
    }

    @Test
    @DisplayName("Service update draft return user not found")
    void updateDraftShouldReturnUserNotFound() {
        when(draftRepository.findById(anyString())).thenReturn(Optional.of(DraftBuilder.getDraft(Media.MOVIE)));
        when(userRepository.existsById(anyString())).thenReturn(false);

        DraftFormDTO form =  DraftBuilder.draftForm(Media.MOVIE);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.updateDraft("61586ad5362766670067eda8", form));
    }

    @Test
    @DisplayName("Service delete draft")
    void deleteDraftShouldReturnSuccessful() {
        when(draftRepository.findById(anyString())).thenReturn(Optional.of(DraftBuilder.getDraft(Media.MOVIE)));
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

}
