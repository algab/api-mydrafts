package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.constants.Gender;
import br.com.mydrafts.ApiMyDrafts.dto.FavoriteDTO;
import br.com.mydrafts.ApiMyDrafts.dto.ProductionDTO;
import br.com.mydrafts.ApiMyDrafts.dto.UserDTO;
import br.com.mydrafts.ApiMyDrafts.services.FavoriteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Favorite Controller")
public class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoriteService service;

    private static final String uriFavorite = "/v1/favorites";

    @Test
    @DisplayName("Controller save favorite")
    public void saveFavoriteShouldReturnSuccessful() throws Exception {
        String json = readFileAsString("/json/favoriteRequest.json");
        when(this.service.save(any())).thenReturn(favorite());

        RequestBuilder request = MockMvcRequestBuilders.post(uriFavorite)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Controller get favorites by user")
    public void getFavoritesShouldReturnSuccessful() throws Exception {
        String json = readFileAsString("/json/favoritesUser.json");
        when(this.service.getFavorites(any(), anyString())).thenReturn(new PageImpl<>(Arrays.asList(favorite()), PageRequest.of(0, 10), 1));

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/users/6158fb48b7179927e035ae7c", uriFavorite))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller delete favorite")
    public void deleteFavoriteShouldReturnSuccessful() throws Exception {
        doNothing().when(this.service).delete(anyString());

        RequestBuilder request = MockMvcRequestBuilders.delete(String.format("%s/6158fb48b7179927e035ae7c", uriFavorite))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNoContent());
    }

    private FavoriteDTO favorite() {
        return FavoriteDTO.builder()
                .id("1")
                .user(user())
                .production(production())
                .build();
    }

    private UserDTO user() {
        return UserDTO.builder()
                .id("61586ad5362766670067edd5")
                .name("Álvaro")
                .email("alvaro@email.com")
                .gender(Gender.MASCULINO)
                .build();
    }

    private ProductionDTO production() {
        return ProductionDTO.builder()
                .id("6158fb48b7179927e035ae7b")
                .tmdbID(550988)
                .media("movie")
                .build();
    }

    private static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(String.format("src/test/resources%s", file))));
    }

}