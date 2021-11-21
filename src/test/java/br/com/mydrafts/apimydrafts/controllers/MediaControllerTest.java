package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.dto.LoginDTO;
import br.com.mydrafts.apimydrafts.repository.UserRepository;
import br.com.mydrafts.apimydrafts.services.MediaService;
import br.com.mydrafts.apimydrafts.builder.MediaBuilder;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Tests for Media Controller")
class MediaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MediaService service;

    @MockBean
    private UserRepository userRepository;

    private String token;

    private static final String PATH_LOGIN = "/v1/login";
    private static final String PATH_MEDIA = "/v1/media";

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
    @DisplayName("Get movie by id")
    void getMovieSuccessful() throws Exception {
        String json = TestUtil.readFileAsString("/json/movie.json");
        when(this.service.getMovie(any(Integer.class))).thenReturn(MediaBuilder.getMovie());

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/movie/1", PATH_MEDIA))
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        mockMvc.perform(request).andExpect(jsonPath("title").value(MediaBuilder.getMovie().getTitle()));
        mockMvc.perform(request).andExpect(jsonPath("overview").value(MediaBuilder.getMovie().getOverview()));
        mockMvc.perform(request).andExpect(jsonPath("dateRelease").value(MediaBuilder.getMovie().getDateRelease().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
    }

    @Test
    @DisplayName("Get tv show by id")
    void getTVSuccessful() throws Exception {
        String json = TestUtil.readFileAsString("/json/tv.json");
        when(this.service.getTV(any(Integer.class))).thenReturn(MediaBuilder.getTV());

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/tv/1", PATH_MEDIA))
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        mockMvc.perform(request).andExpect(jsonPath("title").value(MediaBuilder.getTV().getTitle()));
        mockMvc.perform(request).andExpect(jsonPath("overview").value(MediaBuilder.getTV().getOverview()));
        mockMvc.perform(request).andExpect(jsonPath("dateRelease").value(MediaBuilder.getTV().getDateRelease().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        mockMvc.perform(request).andExpect(jsonPath("lastEpisode").value(MediaBuilder.getTV().getLastEpisode().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
    }

}
