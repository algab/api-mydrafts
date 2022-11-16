package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.exceptions.handler.RestExceptionHandler;
import br.com.mydrafts.apimydrafts.services.FavoriteService;
import br.com.mydrafts.apimydrafts.builder.FavoriteBuilder;
import br.com.mydrafts.apimydrafts.utils.TestUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Favorite Controller")
class FavoriteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FavoriteService service;

    private static final String PATH_FAVORITE = "/v1/favorites";

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FavoriteController(service)).setControllerAdvice(new RestExceptionHandler()).build();
    }

    @Test
    @DisplayName("Controller save favorite")
    void saveFavoriteShouldReturnSuccessful() throws Exception {
        String json = TestUtil.readFileAsString("/json/favoriteRequest.json");
        when(this.service.save(any())).thenReturn(FavoriteBuilder.getFavoriteDTO());

        RequestBuilder request = MockMvcRequestBuilders.post(PATH_FAVORITE)
            .content(json)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Controller delete favorite")
    void deleteFavoriteShouldReturnSuccessful() throws Exception {
        doNothing().when(this.service).delete(anyString());

        RequestBuilder request = MockMvcRequestBuilders.delete(String.format("%s/6158fb48b7179927e035ae7c", PATH_FAVORITE))
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNoContent());
    }

}