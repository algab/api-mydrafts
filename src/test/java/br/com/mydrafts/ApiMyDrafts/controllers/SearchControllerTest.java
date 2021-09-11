package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.constants.Media;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBResultDTO;
import br.com.mydrafts.ApiMyDrafts.services.SearchService;
import br.com.mydrafts.ApiMyDrafts.utils.TrendingUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
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
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = SearchController.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Search Controller")
public class SearchControllerTest {

    private static final String ENDPOINT = "/v1/search";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService service;

    @Test
    @DisplayName("Search movie")
    public void searchMovieTMDB() throws Exception {
        String json = readFileAsString("/json/trending.json");
        when(this.service.searchTMDB(PageRequest.of(0, 10), Media.movie, "spider")).thenReturn(searchMovie());

        RequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT)
                .param("name", "spider")
                .param("media", "movie")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        mockMvc.perform(request).andExpect(jsonPath("totalPages").value(1));
        mockMvc.perform(request).andExpect(jsonPath("totalElements").value(1));
        mockMvc.perform(request).andExpect(jsonPath("content").isArray());
    }

    @Test
    @DisplayName("Search tv show")
    public void searchTVTMDB() throws Exception {
        String json = readFileAsString("/json/trending.json");
        when(this.service.searchTMDB(PageRequest.of(0, 10), Media.tv, "spider")).thenReturn(searchMovie());

        RequestBuilder request = MockMvcRequestBuilders.get(ENDPOINT)
                .param("name", "spider")
                .param("media", "tv")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        mockMvc.perform(request).andExpect(jsonPath("totalPages").value(1));
        mockMvc.perform(request).andExpect(jsonPath("totalElements").value(1));
        mockMvc.perform(request).andExpect(jsonPath("content").isArray());
    }

    private Page<TMDBResultDTO> searchMovie() {
        PageRequest page = PageRequest.of(0, 10);
        List<TMDBResultDTO> contents = Arrays.asList(TrendingUtil.trending());
        return new PageImpl<>(contents, page, 1);
    }

    private static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(String.format("src/test/resources%s", file))));
    }
}