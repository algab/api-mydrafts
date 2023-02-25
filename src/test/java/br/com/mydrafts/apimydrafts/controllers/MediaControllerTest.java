package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.dto.tmdb.MovieResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvResponseDTO;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for MediaController")
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
        MovieResponseDTO movie = MediaFixture.getMovie();
        when(this.service.getMovie(1)).thenReturn(movie);

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/movie/1", PATH_MEDIA));

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("title").value(movie.getTitle()))
            .andExpect(jsonPath("overview").value(movie.getOverview()))
            .andExpect(jsonPath("dateRelease").value(movie.getDateRelease().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
    }

    @Test
    @DisplayName("Get tv show by id")
    void getTVSuccessful() throws Exception {
        TvResponseDTO tv = MediaFixture.getTV();
        when(this.service.getTV(1)).thenReturn(tv);

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/tv/1", PATH_MEDIA));

        mockMvc.perform(request).andExpect(status().isOk())
            .andExpect(jsonPath("title").value(tv.getTitle()))
            .andExpect(jsonPath("overview").value(tv.getOverview()))
            .andExpect(jsonPath("dateRelease").value(tv.getDateRelease().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
            .andExpect(jsonPath("lastEpisode").value(tv.getLastEpisode().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
    }

}
