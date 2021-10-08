package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.services.MediaService;
import br.com.mydrafts.ApiMyDrafts.utils.MediaUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Media Controller")
public class MediaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MediaService service;

    private static final String uriMedia = "/v1/media";

    @Test
    @DisplayName("Get movie by id")
    public void getMovieSuccessful() throws Exception {
        String json = readFileAsString("/json/movie.json");
        when(this.service.getMovie(any(Integer.class))).thenReturn(MediaUtil.getMovie());

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/movie/1", uriMedia))
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
        String json = readFileAsString("/json/tv.json");
        when(this.service.getTV(any(Integer.class))).thenReturn(MediaUtil.getTV());

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/tv/1", uriMedia))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        mockMvc.perform(request).andExpect(jsonPath("title").value(MediaUtil.getTV().getTitle()));
        mockMvc.perform(request).andExpect(jsonPath("overview").value(MediaUtil.getTV().getOverview()));
        mockMvc.perform(request).andExpect(jsonPath("dateRelease").value(MediaUtil.getTV().getDateRelease().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        mockMvc.perform(request).andExpect(jsonPath("lastEpisode").value(MediaUtil.getTV().getLastEpisode().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
    }

    private static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(String.format("src/test/resources%s", file))));
    }

}
