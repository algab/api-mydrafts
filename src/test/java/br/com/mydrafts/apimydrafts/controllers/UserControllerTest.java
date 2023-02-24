package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.dto.DraftDTO;
import br.com.mydrafts.apimydrafts.dto.FavoriteDTO;
import br.com.mydrafts.apimydrafts.dto.ProductionDTO;
import br.com.mydrafts.apimydrafts.dto.UserFormDTO;
import br.com.mydrafts.apimydrafts.exceptions.handler.RestExceptionHandler;
import br.com.mydrafts.apimydrafts.fixtures.ProductionFixture;
import br.com.mydrafts.apimydrafts.services.UserService;
import br.com.mydrafts.apimydrafts.fixtures.DraftFixture;
import br.com.mydrafts.apimydrafts.fixtures.FavoriteFixture;
import br.com.mydrafts.apimydrafts.fixtures.UserFixture;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for User Controller")
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService service;

    private static final Gson gson = new Gson();

    private static final String PATH_USER = "/v1/users";

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(service))
            .setControllerAdvice(new RestExceptionHandler())
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .build();
    }

    @Test
    @DisplayName("Controller save user")
    void saveUserShouldReturnSuccessful() throws Exception {
        when(this.service.save(UserFixture.getUserForm())).thenReturn(UserFixture.getUserDTO());

        RequestBuilder request = MockMvcRequestBuilders.post(PATH_USER)
            .content(gson.toJson(UserFixture.getUserForm()))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Controller save user bad request")
    void saveUserShouldReturnBadRequest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post(PATH_USER)
            .content(gson.toJson(UserFormDTO.builder().firstName("Test").build()))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Controller search user by id")
    void searchUserShouldReturnSuccessful() throws Exception {
        when(this.service.search(anyString())).thenReturn(UserFixture.getUserDTO());

        RequestBuilder request = MockMvcRequestBuilders.get(
            String.format("%s/%s", PATH_USER, UserFixture.getUser().getId())
        );

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller get drafts by user")
    void getDraftsByUserShouldReturnSuccessful() throws Exception {
        ProductionDTO production = ProductionFixture.getProductionMovieDTO();
        DraftDTO draft = DraftFixture.getDraftDTO(production);
        Page<DraftDTO> drafts = new PageImpl<>(
            Collections.singletonList(draft),
            PageRequest.of(0, 10),
            1
        );
        when(this.service.getDrafts(any(), anyString())).thenReturn(drafts);

        RequestBuilder request = MockMvcRequestBuilders.get(
            String.format("%s/%s/drafts", PATH_USER, UserFixture.getUser().getId())
        );

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller get favorites by user")
    void getFavoritesByUserShouldReturnSuccessful() throws Exception {
        Page<FavoriteDTO> favorites = new PageImpl<>(
            Collections.singletonList(FavoriteFixture.getFavoriteDTO()),
            PageRequest.of(0, 10),
            1
        );
        when(this.service.getFavorites(any(), anyString())).thenReturn(favorites);

        RequestBuilder request = MockMvcRequestBuilders.get(
            String.format("%s/%s/favorites", PATH_USER, UserFixture.getUser().getId())
        );

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller update user by id")
    void updateUserShouldReturnSuccessful() throws Exception {
        when(this.service.update(anyString(), any())).thenReturn(UserFixture.getUserDTO());

        RequestBuilder request = MockMvcRequestBuilders.put(String.format("%s/%s", PATH_USER, UserFixture.getUser().getId()))
            .content(gson.toJson(UserFixture.getUserForm()))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller delete user by id")
    void deleteUserShouldReturnSuccessful() throws Exception {
        doNothing().when(this.service).delete(anyString());

        RequestBuilder request = MockMvcRequestBuilders.delete(
            String.format("%s/%s", PATH_USER, UserFixture.getUser().getId())
        );

        mockMvc.perform(request).andExpect(status().isNoContent());
    }

}
