package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.dto.LoginDTO;
import br.com.mydrafts.apimydrafts.dto.TMDBResultDTO;
import br.com.mydrafts.apimydrafts.repository.UserRepository;
import br.com.mydrafts.apimydrafts.services.SearchService;
import br.com.mydrafts.apimydrafts.builder.SearchBuilder;
import br.com.mydrafts.apimydrafts.utils.TestUtil;
import br.com.mydrafts.apimydrafts.builder.UserBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Tests for Search Controller")
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService service;

    @MockBean
    private UserRepository userRepository;

    private String token;

    private static final String PATH_LOGIN = "/v1/login";
    private static final String PATH_SEARCH = "/v1/search";

    @BeforeAll
    public void init() throws Exception {
        String json = TestUtil.readFileAsString("/json/login.json");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(UserBuilder.getUser()));

        RequestBuilder request = MockMvcRequestBuilders.post(PATH_LOGIN)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request).andReturn();
        token = new ObjectMapper().readValue(result.getResponse().getContentAsString(), LoginDTO.class).getToken();
    }

    @Test
    @DisplayName("Search movie")
    void searchMovieTMDB() throws Exception {
        String json = TestUtil.readFileAsString("/json/searchMovie.json");
        when(this.service.searchTMDB(PageRequest.of(0, 10), Media.MOVIE, "shang")).thenReturn(searchMovie());

        RequestBuilder request = MockMvcRequestBuilders.get(PATH_SEARCH)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
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
        String json = TestUtil.readFileAsString("/json/searchTV.json");
        when(this.service.searchTMDB(PageRequest.of(0, 10), Media.TV, "what")).thenReturn(searchTV());

        RequestBuilder request = MockMvcRequestBuilders.get(PATH_SEARCH)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
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
        String json = TestUtil.readFileAsString("/json/searchTV.json");
        when(this.service.searchTMDB(PageRequest.of(0, 10), null, "what")).thenReturn(searchTV());

        RequestBuilder request = MockMvcRequestBuilders.get(PATH_SEARCH)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                .param("name", "what")
                .param("media", "test")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        mockMvc.perform(request).andExpect(jsonPath("totalPages").value(1));
        mockMvc.perform(request).andExpect(jsonPath("totalElements").value(1));
        mockMvc.perform(request).andExpect(jsonPath("content").isArray());
    }

    private Page<TMDBResultDTO> searchMovie() {
        PageRequest page = PageRequest.of(0, 10);
        List<TMDBResultDTO> contents = Arrays.asList(SearchBuilder.searchMovie());
        return new PageImpl<>(contents, page, 1);
    }

    private Page<TMDBResultDTO> searchTV() {
        PageRequest page = PageRequest.of(0, 10);
        List<TMDBResultDTO> contents = Arrays.asList(SearchBuilder.searchTV());
        return new PageImpl<>(contents, page, 1);
    }

}
