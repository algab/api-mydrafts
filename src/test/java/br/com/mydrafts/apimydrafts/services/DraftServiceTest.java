package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.DraftDocument;
import br.com.mydrafts.apimydrafts.documents.ProductionDocument;
import br.com.mydrafts.apimydrafts.dto.DraftDTO;
import br.com.mydrafts.apimydrafts.dto.DraftFormDTO;
import br.com.mydrafts.apimydrafts.dto.DraftUpdateFormDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.repository.DraftRepository;
import br.com.mydrafts.apimydrafts.repository.UserRepository;
import br.com.mydrafts.apimydrafts.fixtures.DraftFixture;
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
@DisplayName("Tests for DraftService")
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
        ProductionDocument production = ProductionFixture.getProductionMovie();
        DraftDocument draft = DraftFixture.getDraft(production);
        when(userRepository.findById("61586ad5362766670067edd5")).thenReturn(Optional.of(UserFixture.getUser()));
        when(productionService.searchProduction(550988, Media.MOVIE))
            .thenReturn(Optional.of(ProductionFixture.getProductionMovie()));
        when(draftRepository.existsByUserAndProduction(UserFixture.getUser(), ProductionFixture.getProductionMovie()))
            .thenReturn(false);
        when(draftRepository.save(any(DraftDocument.class))).thenReturn(draft);

        DraftDTO draftDTO = service.save(DraftFixture.getDraftForm(550988, Media.MOVIE, null));

        assertThat(draftDTO.getDescription()).isEqualTo(draft.getDescription());
        assertThat(draftDTO.getProduction().getId()).isEqualTo(draft.getProduction().getId());
    }

    @Test
    @DisplayName("Service save draft production movie")
    void saveDraftProductionMovieShouldReturnSuccessful() {
        ProductionDocument production = ProductionFixture.getProductionMovie();
        DraftDocument draft = DraftFixture.getDraft(production);
        when(userRepository.findById("61586ad5362766670067edd5")).thenReturn(Optional.of(UserFixture.getUser()));
        when(productionService.searchProduction(550988, Media.MOVIE)).thenReturn(Optional.empty());
        when(productionService.mountProduction(550988, Media.MOVIE)).thenReturn(production);
        when(draftRepository.save(any(DraftDocument.class))).thenReturn(draft);

        DraftDTO draftDTO = service.save(DraftFixture.getDraftForm(550988, Media.MOVIE, null));

        assertThat(draftDTO.getDescription()).isEqualTo(draft.getDescription());
        assertThat(draftDTO.getProduction().getId()).isEqualTo(draft.getProduction().getId());
    }

    @Test
    @DisplayName("Service save draft production tv with season")
    void saveDraftProductionTVWithSeasonShouldReturnSuccessful() {
        ProductionDocument production = ProductionFixture.getProductionTV();
        DraftDocument draft = DraftFixture.getDraft(production);
        when(userRepository.findById("61586ad5362766670067edd5")).thenReturn(Optional.of(UserFixture.getUser()));
        when(productionService.searchProduction(550989, Media.TV)).thenReturn(Optional.empty());
        when(productionService.mountProduction(550989, Media.TV)).thenReturn(production);
        when(draftRepository.save(any(DraftDocument.class))).thenReturn(draft);

        DraftDTO draftDTO = service.save(DraftFixture.getDraftForm(550989, Media.TV, 1));

        assertThat(draftDTO.getProduction().getMedia()).isEqualTo(draft.getProduction().getMedia());
    }

    @Test
    @DisplayName("Service save draft production tv already registered")
    void saveDraftProductionTVAlreadyRegisteredShouldReturnSuccessful() {
        ProductionDocument production = ProductionFixture.getProductionTV();
        DraftDocument draft = DraftFixture.getDraft(production);
        when(userRepository.findById("61586ad5362766670067edd5")).thenReturn(Optional.of(UserFixture.getUser()));
        when(productionService.searchProduction(550989, Media.TV)).thenReturn(Optional.of(production));
        when(productionService.mountProduction(550989, Media.TV)).thenReturn(production);
        when(draftRepository.save(any(DraftDocument.class))).thenReturn(draft);

        DraftDTO draftDTO = service.save(DraftFixture.getDraftForm(550989, Media.TV, 1));

        assertThat(draftDTO.getProduction().getMedia()).isEqualTo(draft.getProduction().getMedia());
    }

    @Test
    @DisplayName("Service save draft production tv without season")
    void saveDraftProductionTVShouldReturnBadRequest() {
        when(userRepository.findById("61586ad5362766670067edd5")).thenReturn(Optional.of(UserFixture.getUser()));
        when(productionService.searchProduction(550989, Media.TV)).thenReturn(Optional.empty());

        DraftFormDTO form = DraftFixture.getDraftForm(550989, Media.TV, null);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(form));
    }

    @Test
    @DisplayName("Service save draft production tv with season incorrect")
    void saveDraftProductionTVWithSeasonIncorrectShouldReturnBadRequest() {
        ProductionDocument production = ProductionFixture.getProductionTV();
        when(userRepository.findById("61586ad5362766670067edd5")).thenReturn(Optional.of(UserFixture.getUser()));
        when(productionService.searchProduction(550989, Media.TV)).thenReturn(Optional.empty());
        when(productionService.mountProduction(550989, Media.TV)).thenReturn(production);

        DraftFormDTO form = DraftFixture.getDraftForm(550989, Media.TV, 10);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(form));
    }

    @Test
    @DisplayName("Service draft already registered")
    void saveDraftShouldReturnAlreadyRegistered() {
        when(userRepository.findById("61586ad5362766670067edd5")).thenReturn(Optional.of(UserFixture.getUser()));
        when(productionService.searchProduction(550988, Media.MOVIE))
            .thenReturn(Optional.of(ProductionFixture.getProductionMovie()));
        when(draftRepository.existsByUserAndProduction(UserFixture.getUser(), ProductionFixture.getProductionMovie()))
            .thenReturn(true);

        DraftFormDTO form = DraftFixture.getDraftForm(550988, Media.MOVIE, null);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(form));
    }

    @Test
    @DisplayName("Service save draft user not found")
    void saveDraftShouldReturnUserNotFound() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        DraftFormDTO form = DraftFixture.getDraftForm(550988, Media.TV, null);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(form));
    }

    @Test
    @DisplayName("Service search draft by id")
    void searchDraftShouldReturnSuccessful() {
        ProductionDocument production = ProductionFixture.getProductionMovie();
        DraftDocument draft = DraftFixture.getDraft(production);
        when(draftRepository.findById("61586ad5362766670067eda8")).thenReturn(Optional.of(draft));

        DraftDTO draftDTO = service.searchDraft("61586ad5362766670067eda8");

        assertThat(draftDTO.getProduction().getMedia()).isEqualTo(Media.MOVIE);
        assertThat(draftDTO.getRating()).isEqualTo(draft.getRating());
        assertThat(draftDTO.getDescription()).isEqualTo(draft.getDescription());
    }

    @Test
    @DisplayName("Service search draft not found")
    void searchDraftShouldReturnNotFound() {
        when(draftRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> service.searchDraft("61586ad5362766670067eda8"));
    }

    @Test
    @DisplayName("Service update draft production movie")
    void updateDraftShouldReturnSuccessful() {
        ProductionDocument production = ProductionFixture.getProductionMovie();
        DraftDocument draft = DraftFixture.getDraft(production);
        when(draftRepository.findById("61586ad5362766670067eda8")).thenReturn(Optional.of(draft));
        when(userRepository.existsById("61586ad5362766670067edd5")).thenReturn(true);
        when(draftRepository.save(draft)).thenReturn(draft);

        DraftDTO draftDTO = service.updateDraft("61586ad5362766670067eda8", DraftFixture.getDraftUpdateForm(null));

        assertThat(draftDTO.getProduction().getId()).isEqualTo(draft.getProduction().getId());
        assertThat(draftDTO.getProduction().getMedia()).isEqualTo(draft.getProduction().getMedia());
    }

    @Test
    @DisplayName("Service update draft production tv")
    void updateDraftProductionTvShouldReturnSuccessful() {
        ProductionDocument production = ProductionFixture.getProductionTV();
        DraftDocument draft = DraftFixture.getDraft(production);
        when(draftRepository.findById("61586ad5362766670067eda8")).thenReturn(Optional.of(draft));
        when(userRepository.existsById("61586ad5362766670067edd5")).thenReturn(true);
        when(draftRepository.save(draft)).thenReturn(draft);

        DraftDTO draftDTO = service.updateDraft("61586ad5362766670067eda8", DraftFixture.getDraftUpdateForm(1));

        assertThat(draftDTO.getProduction().getId()).isEqualTo(draft.getProduction().getId());
        assertThat(draftDTO.getProduction().getMedia()).isEqualTo(draft.getProduction().getMedia());
    }

    @Test
    @DisplayName("Service update draft production tv without season")
    void updateDraftProductionTvShouldReturnBadRequest() {
        ProductionDocument production = ProductionFixture.getProductionTV();
        DraftDocument draft = DraftFixture.getDraft(production);
        when(draftRepository.findById("61586ad5362766670067eda8")).thenReturn(Optional.of(draft));
        when(userRepository.existsById("61586ad5362766670067edd5")).thenReturn(true);

        DraftUpdateFormDTO draftForm = DraftFixture.getDraftUpdateForm(null);

        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> service.updateDraft("61586ad5362766670067eda8", draftForm));
    }

    @Test
    @DisplayName("Service update draft production tv with season incorrect")
    void updateDraftProductionTvWithSeasonIncorrectShouldReturnBadRequest() {
        ProductionDocument production = ProductionFixture.getProductionTV();
        DraftDocument draft = DraftFixture.getDraft(production);
        when(draftRepository.findById("61586ad5362766670067eda8")).thenReturn(Optional.of(draft));
        when(userRepository.existsById("61586ad5362766670067edd5")).thenReturn(true);

        DraftUpdateFormDTO draftForm = DraftFixture.getDraftUpdateForm(10);

        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> service.updateDraft("61586ad5362766670067eda8", draftForm));
    }

    @Test
    @DisplayName("Service update draft not found")
    void updateDraftShouldReturnDraftNotFound() {
        when(draftRepository.findById("61586ad5362766670067eda8")).thenReturn(Optional.empty());

        DraftUpdateFormDTO form = DraftFixture.getDraftUpdateForm(null);

        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> service.updateDraft("61586ad5362766670067eda8", form));
    }

    @Test
    @DisplayName("Service update draft return user not found")
    void updateDraftShouldReturnUserNotFound() {
        ProductionDocument production = ProductionFixture.getProductionMovie();
        DraftDocument draft = DraftFixture.getDraft(production);
        when(draftRepository.findById("61586ad5362766670067eda8")).thenReturn(Optional.of(draft));
        when(userRepository.existsById("61586ad5362766670067edd5")).thenReturn(false);

        DraftUpdateFormDTO form =  DraftFixture.getDraftUpdateForm(null);

        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> service.updateDraft("61586ad5362766670067eda8", form));
    }

    @Test
    @DisplayName("Service delete draft")
    void deleteDraftShouldReturnSuccessful() {
        ProductionDocument production = ProductionFixture.getProductionMovie();
        DraftDocument draft = DraftFixture.getDraft(production);
        when(draftRepository.findById("61586ad5362766670067eda8")).thenReturn(Optional.of(draft));
        doNothing().when(draftRepository).delete(any());

        service.deleteDraft("61586ad5362766670067eda8");

        verify(draftRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Service delete draft not found")
    void deleteDraftShouldReturnNotFound() {
        when(draftRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> service.deleteDraft("61586ad5362766670067eda8"));
    }

}
