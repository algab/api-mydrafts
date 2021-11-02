package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.services.DraftService;
import br.com.mydrafts.ApiMyDrafts.utils.DraftUtil;
import br.com.mydrafts.ApiMyDrafts.utils.TestUtil;
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
        String json = TestUtil.readFileAsString("/json/draftRequest.json");
        when(this.service.save(any())).thenReturn(DraftUtil.getDraftDTO());

        RequestBuilder request = MockMvcRequestBuilders.post(uriDraft)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Controller search draft by id")
    public void searchDraftShouldReturnSuccessful() throws Exception {
        String json = TestUtil.readFileAsString("/json/draft.json");
        when(this.service.searchDraft(anyString())).thenReturn(DraftUtil.getDraftDTO());

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/6158fb48b7179927e035ae7c", uriDraft))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller update draft")
    public void updateDraftShouldReturnSuccessful() throws Exception {
        String json = TestUtil.readFileAsString("/json/draftRequest.json");
        when(this.service.updateDraft(anyString(), any())).thenReturn(DraftUtil.getDraftDTO());

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

}
