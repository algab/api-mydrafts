package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.exceptions.handler.RestExceptionHandler;
import br.com.mydrafts.apimydrafts.services.MediaService;
import br.com.mydrafts.apimydrafts.fixtures.MediaFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Media Controller")
class MediaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MediaService service;

    private static final String PATH_MEDIA = "/v1/media";

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MediaController(service))
            .setControllerAdvice(new RestExceptionHandler())
            .build();
    }

    @Test
    @DisplayName("Get movie by id")
    void getMovieSuccessful() throws Exception {
        when(this.service.getMovie(any(Integer.class))).thenReturn(MediaFixture.getMovie());

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/movie/1", PATH_MEDIA));

        mockMvc.perform(request).andExpect(status().isOk());
        mockMvc.perform(request).andExpect(jsonPath("title").value(MediaFixture.getMovie().getTitle()));
        mockMvc.perform(request).andExpect(jsonPath("overview").value(MediaFixture.getMovie().getOverview()));
        mockMvc.perform(request).andExpect(jsonPath("dateRelease").value(MediaFixture.getMovie().getDateRelease().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
    }

    @Test
    @DisplayName("Get tv show by id")
    void getTVSuccessful() throws Exception {
        when(this.service.getTV(any(Integer.class))).thenReturn(MediaFixture.getTV());

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/tv/1", PATH_MEDIA));

        mockMvc.perform(request).andExpect(status().isOk());
        mockMvc.perform(request).andExpect(jsonPath("title").value(MediaFixture.getTV().getTitle()));
        mockMvc.perform(request).andExpect(jsonPath("overview").value(MediaFixture.getTV().getOverview()));
        mockMvc.perform(request).andExpect(jsonPath("dateRelease").value(MediaFixture.getTV().getDateRelease().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        mockMvc.perform(request).andExpect(jsonPath("lastEpisode").value(MediaFixture.getTV().getLastEpisode().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
    }

}
