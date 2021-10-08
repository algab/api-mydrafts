package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.constants.Gender;
import br.com.mydrafts.ApiMyDrafts.dto.DraftDTO;
import br.com.mydrafts.ApiMyDrafts.dto.ProductionDTO;
import br.com.mydrafts.ApiMyDrafts.dto.UserDTO;
import br.com.mydrafts.ApiMyDrafts.services.DraftService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Draft Controller")
public class DraftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DraftService service;

    private static final String uriDraft = "/v1/drafts";

    @Test
    @DisplayName("Controller save draft")
    public void saveDraftShouldReturnSuccessful() throws Exception {
        String json = readFileAsString("/json/draftRequest.json");
        when(this.service.save(any())).thenReturn(draft());

        RequestBuilder request = MockMvcRequestBuilders.post(uriDraft)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Controller get drafts by user")
    public void getDraftsByUserShouldReturnSuccessful() throws Exception {
        String json = readFileAsString("/json/draftsUser.json");
        when(this.service.getDrafts(any(), anyString())).thenReturn(new PageImpl<>(Arrays.asList(drafts()), PageRequest.of(0, 10), 1));

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/users/61586ad5362766670067edd5", uriDraft))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller search draft by id")
    public void searchDraftShouldReturnSuccessful() throws Exception {
        String json = readFileAsString("/json/draft.json");
        when(this.service.searchDraft(anyString())).thenReturn(drafts());

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/6158fb48b7179927e035ae7c", uriDraft))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller update draft")
    public void updateDraftShouldReturnSuccessful() throws Exception {
        String json = readFileAsString("/json/draftRequest.json");
        when(this.service.updateDraft(anyString(), any())).thenReturn(draft());

        RequestBuilder request = MockMvcRequestBuilders.put(String.format("%s/6158fb48b7179927e035ae7c", uriDraft))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller delete draft by id")
    public void deleteDraftShouldReturnSuccessful() throws Exception {
        doNothing().when(this.service).deleteDraft(anyString());

        RequestBuilder request = MockMvcRequestBuilders.delete(String.format("%s/6158fb48b7179927e035ae7c", uriDraft))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNoContent());
    }

    private DraftDTO draft() {
        return DraftDTO.builder()
                .id("1")
                .description("Teste")
                .rating(10D)
                .build();
    }

    private DraftDTO drafts() {
        return DraftDTO.builder()
                .id("6158fb48b7179927e035ae7c")
                .description("Teste")
                .rating(10D)
                .user(user())
                .production(production())
                .build();
    }

    private UserDTO user() {
        return UserDTO.builder()
                .id("61586ad5362766670067edd5")
                .name("Álvaro")
                .email("alvaro@email.com")
                .gender(Gender.MASCULINO)
                .build();
    }

    private ProductionDTO production() {
        return ProductionDTO.builder()
                .id("6158fb48b7179927e035ae7b")
                .tmdbID(550988)
                .media("movie")
                .build();
    }

    private static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(String.format("src/test/resources%s", file))));
    }

}
