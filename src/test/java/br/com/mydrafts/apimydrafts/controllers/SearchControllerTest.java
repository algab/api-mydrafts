package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResultDTO;
import br.com.mydrafts.apimydrafts.exceptions.handler.RestExceptionHandler;
import br.com.mydrafts.apimydrafts.services.SearchService;
import br.com.mydrafts.apimydrafts.fixtures.SearchFixture;
import br.com.mydrafts.apimydrafts.TestUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Search Controller")
class SearchControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SearchService service;

    private static final String PATH_SEARCH = "/v1/search";

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new SearchController(service))
            .setControllerAdvice(new RestExceptionHandler())
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .build();
    }

    @Test
    @DisplayName("Search movie")
    void searchMovieTMDB() throws Exception {
        String json = TestUtils.readFileAsString("/json/searchMovie.json");
        when(this.service.searchTMDB(PageRequest.of(0, 10), Media.MOVIE, "shang")).thenReturn(searchMovie());

        RequestBuilder request = MockMvcRequestBuilders.get(PATH_SEARCH)
            .param("name", "shang")
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
    void searchTVTMDB() throws Exception {
        String json = TestUtils.readFileAsString("/json/searchTV.json");
        when(this.service.searchTMDB(PageRequest.of(0, 10), Media.TV, "what")).thenReturn(searchTV());

        RequestBuilder request = MockMvcRequestBuilders.get(PATH_SEARCH)
            .param("name", "what")
            .param("media", "tv")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        mockMvc.perform(request).andExpect(jsonPath("totalPages").value(1));
        mockMvc.perform(request).andExpect(jsonPath("totalElements").value(1));
        mockMvc.perform(request).andExpect(jsonPath("content").isArray());
    }

    @Test
    @DisplayName("Search any media")
    void searchAnyMediaTMDB() throws Exception {
        String json = TestUtils.readFileAsString("/json/searchTV.json");
        when(this.service.searchTMDB(PageRequest.of(0, 10), null, "what")).thenReturn(searchTV());

        RequestBuilder request = MockMvcRequestBuilders.get(PATH_SEARCH)
            .param("name", "what")
            .param("media", "test")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        mockMvc.perform(request).andExpect(jsonPath("totalPages").value(1));
        mockMvc.perform(request).andExpect(jsonPath("totalElements").value(1));
        mockMvc.perform(request).andExpect(jsonPath("content").isArray());
    }

    private Page<ResultDTO> searchMovie() {
        PageRequest page = PageRequest.of(0, 10);
        List<ResultDTO> contents = Collections.singletonList(SearchFixture.searchMovie());
        return new PageImpl<>(contents, page, 1);
    }

    private Page<ResultDTO> searchTV() {
        PageRequest page = PageRequest.of(0, 10);
        List<ResultDTO> contents = Collections.singletonList(SearchFixture.searchTV());
        return new PageImpl<>(contents, page, 1);
    }

}
