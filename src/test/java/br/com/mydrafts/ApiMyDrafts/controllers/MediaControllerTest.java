package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.dto.LoginDTO;
import br.com.mydrafts.ApiMyDrafts.repository.UserRepository;
import br.com.mydrafts.ApiMyDrafts.services.MediaService;
import br.com.mydrafts.ApiMyDrafts.utils.MediaUtil;
import br.com.mydrafts.ApiMyDrafts.utils.TestUtil;
import br.com.mydrafts.ApiMyDrafts.utils.UserUtil;
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
public class MediaControllerTest {

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
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(UserUtil.getUser()));

        RequestBuilder request = MockMvcRequestBuilders.post(PATH_LOGIN)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request).andReturn();
        token = new ObjectMapper().readValue(result.getResponse().getContentAsString(), LoginDTO.class).getToken();
    }

    @Test
    @DisplayName("Get movie by id")
    public void getMovieSuccessful() throws Exception {
        String json = TestUtil.readFileAsString("/json/movie.json");
        when(this.service.getMovie(any(Integer.class))).thenReturn(MediaUtil.getMovie());

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/movie/1", PATH_MEDIA))
                .header("Authorization", String.format("Bearer %s", token))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        mockMvc.perform(request).andExpect(jsonPath("title").value(MediaUtil.getMovie().getTitle()));
        mockMvc.perform(request).andExpect(jsonPath("overview").value(MediaUtil.getMovie().getOverview()));
        mockMvc.perform(request).andExpect(jsonPath("dateRelease").value(MediaUtil.getMovie().getDateRelease().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
    }

    @Test
    @DisplayName("Get tv show by id")
    public void getTVSuccessful() throws Exception {
        String json = TestUtil.readFileAsString("/json/tv.json");
        when(this.service.getTV(any(Integer.class))).thenReturn(MediaUtil.getTV());

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/tv/1", PATH_MEDIA))
                .header("Authorization", String.format("Bearer %s", token))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        mockMvc.perform(request).andExpect(jsonPath("title").value(MediaUtil.getTV().getTitle()));
        mockMvc.perform(request).andExpect(jsonPath("overview").value(MediaUtil.getTV().getOverview()));
        mockMvc.perform(request).andExpect(jsonPath("dateRelease").value(MediaUtil.getTV().getDateRelease().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        mockMvc.perform(request).andExpect(jsonPath("lastEpisode").value(MediaUtil.getTV().getLastEpisode().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
    }

}
