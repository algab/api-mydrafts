package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.dto.TMDBResultDTO;
import br.com.mydrafts.ApiMyDrafts.services.TrendingService;
import br.com.mydrafts.ApiMyDrafts.utils.TestUtil;
import br.com.mydrafts.ApiMyDrafts.utils.TrendingUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Trending Controller")
public class TrendingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrendingService service;

    private static final String uriTrending = "/v1/trending";

    @Test
    @DisplayName("Controller trending movies and tv")
    public void trendingTMDB() throws Exception {
        String json = TestUtil.readFileAsString("/json/trending.json");
        when(this.service.trendingTMDB(PageRequest.of(0, 10))).thenReturn(trending());

        RequestBuilder request = MockMvcRequestBuilders.get(uriTrending).content(json).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        mockMvc.perform(request).andExpect(jsonPath("totalPages").value(1));
        mockMvc.perform(request).andExpect(jsonPath("totalElements").value(1));
        mockMvc.perform(request).andExpect(jsonPath("content").isArray());
    }

    private Page<TMDBResultDTO> trending() {
        PageRequest page = PageRequest.of(0, 10);
        List<TMDBResultDTO> contents = Arrays.asList(TrendingUtil.trending());
        return new PageImpl<>(contents, page, 1);
    }

}
