package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.dto.DraftDTO;
import br.com.mydrafts.apimydrafts.dto.ProductionDTO;
import br.com.mydrafts.apimydrafts.exceptions.handler.RestExceptionHandler;
import br.com.mydrafts.apimydrafts.fixtures.ProductionFixture;
import br.com.mydrafts.apimydrafts.services.DraftService;
import br.com.mydrafts.apimydrafts.fixtures.DraftFixture;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Draft Controller")
class DraftControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DraftService service;

    private static final Gson gson = new Gson();

    private static final String PATH_DRAFT = "/v1/drafts";

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DraftController(service))
            .setControllerAdvice(new RestExceptionHandler())
            .build();
    }

    @Test
    @DisplayName("Controller save draft")
    void saveDraftShouldReturnSuccessful() throws Exception {
        ProductionDTO production = ProductionFixture.getProductionMovieDTO();
        DraftDTO draft = DraftFixture.getDraftDTO(production);
        when(this.service.save(any())).thenReturn(draft);

        RequestBuilder request = MockMvcRequestBuilders.post(PATH_DRAFT)
            .content(gson.toJson(DraftFixture.getDraftForm(550988, Media.MOVIE, null)))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Controller search draft by id")
    void searchDraftShouldReturnSuccessful() throws Exception {
        ProductionDTO production = ProductionFixture.getProductionMovieDTO();
        when(this.service.searchDraft(anyString())).thenReturn(DraftFixture.getDraftDTO(production));

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/6158fb48b7179927e035ae7c", PATH_DRAFT));

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller update draft")
    void updateDraftShouldReturnSuccessful() throws Exception {
        ProductionDTO production = ProductionFixture.getProductionMovieDTO();
        when(this.service.updateDraft(anyString(), any())).thenReturn(DraftFixture.getDraftDTO(production));

        RequestBuilder request = MockMvcRequestBuilders.put(String.format("%s/6158fb48b7179927e035ae7c", PATH_DRAFT))
            .content(gson.toJson(DraftFixture.getDraftUpdateForm(null)))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller delete draft by id")
    void deleteDraftShouldReturnSuccessful() throws Exception {
        doNothing().when(this.service).deleteDraft(anyString());

        RequestBuilder request = MockMvcRequestBuilders.delete(String.format("%s/6158fb48b7179927e035ae7c", PATH_DRAFT));

        mockMvc.perform(request).andExpect(status().isNoContent());
    }

}
