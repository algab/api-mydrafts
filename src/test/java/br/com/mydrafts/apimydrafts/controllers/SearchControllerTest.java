package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.exceptions.handler.RestExceptionHandler;
import br.com.mydrafts.apimydrafts.services.SearchService;
import br.com.mydrafts.apimydrafts.fixtures.SearchFixture;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for SearchController")
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
        when(this.service.searchTMDB(PageRequest.of(0, 10), Media.MOVIE, "shang"))
            .thenReturn(SearchFixture.getPageSearchMovie());

        RequestBuilder request = MockMvcRequestBuilders.get(PATH_SEARCH)
            .param("name", "shang")
            .param("media", "movie");

        mockMvc.perform(request).andExpect(status().isOk())
            .andExpect(jsonPath("totalPages").value(1))
            .andExpect(jsonPath("totalElements").value(1))
            .andExpect(jsonPath("content").isArray());
    }

    @Test
    @DisplayName("Search tv show")
    void searchTVTMDB() throws Exception {
        when(this.service.searchTMDB(PageRequest.of(0, 10), Media.TV, "what"))
            .thenReturn(SearchFixture.getPageSearchTV());

        RequestBuilder request = MockMvcRequestBuilders.get(PATH_SEARCH)
            .param("name", "what")
            .param("media", "tv");

        mockMvc.perform(request).andExpect(status().isOk())
            .andExpect(jsonPath("totalPages").value(1))
            .andExpect(jsonPath("totalElements").value(1))
            .andExpect(jsonPath("content").isArray());
    }

    @Test
    @DisplayName("Search any media")
    void searchAnyMediaTMDB() throws Exception {
        when(this.service.searchTMDB(PageRequest.of(0, 10), null, "what"))
            .thenReturn(SearchFixture.getPageSearchTV());

        RequestBuilder request = MockMvcRequestBuilders.get(PATH_SEARCH)
            .param("name", "what")
            .param("media", "test");

        mockMvc.perform(request).andExpect(status().isOk())
            .andExpect(jsonPath("totalPages").value(1))
            .andExpect(jsonPath("totalElements").value(1))
            .andExpect(jsonPath("content").isArray());
    }

}
