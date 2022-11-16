package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.exceptions.handler.RestExceptionHandler;
import br.com.mydrafts.apimydrafts.services.UserService;
import br.com.mydrafts.apimydrafts.fixtures.DraftFixture;
import br.com.mydrafts.apimydrafts.fixtures.FavoriteFixture;
import br.com.mydrafts.apimydrafts.utils.TestUtil;
import br.com.mydrafts.apimydrafts.fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
        String json = TestUtil.readFileAsString("/json/userRequest.json");
        when(this.service.saveUser(any())).thenReturn(UserFixture.getUserDTO());

        RequestBuilder request = MockMvcRequestBuilders.post(PATH_USER)
            .content(json)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Controller save user bad request")
    void saveUserShouldReturnBadRequest() throws Exception {
        String json = TestUtil.readFileAsString("/json/userBadRequest.json");
        when(this.service.saveUser(any())).thenReturn(UserFixture.getUserDTO());

        RequestBuilder request = MockMvcRequestBuilders.post(PATH_USER)
            .content(json)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Controller search user by id")
    void searchUserShouldReturnSuccessful() throws Exception {
        String json = TestUtil.readFileAsString("/json/user.json");
        when(this.service.searchUser(anyString())).thenReturn(UserFixture.getUserDTO());

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/%s", PATH_USER, UserFixture.getUser().getId()))
            .content(json)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller get drafts by user")
    void getDraftsByUserShouldReturnSuccessful() throws Exception {
        String json = TestUtil.readFileAsString("/json/draftsUser.json");
        when(this.service.getDrafts(any(), anyString()))
            .thenReturn(new PageImpl<>(Collections.singletonList(DraftFixture.getDraftDTO()), PageRequest.of(0, 10), 1));

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/%s/drafts", PATH_USER, UserFixture.getUser().getId()))
            .content(json)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller get favorites by user")
    void getFavoritesByUserShouldReturnSuccessful() throws Exception {
        String json = TestUtil.readFileAsString("/json/favoritesUser.json");
        when(this.service.getFavorites(any(), anyString()))
            .thenReturn(new PageImpl<>(Collections.singletonList(FavoriteFixture.getFavoriteDTO()), PageRequest.of(0, 10), 1));

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/%s/favorites", PATH_USER, UserFixture.getUser().getId()))
            .content(json)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller update user by id")
    void updateUserShouldReturnSuccessful() throws Exception {
        String json = TestUtil.readFileAsString("/json/userRequest.json");
        when(this.service.updateUser(anyString(), any())).thenReturn(UserFixture.getUserDTO());

        RequestBuilder request = MockMvcRequestBuilders.put(String.format("%s/%s", PATH_USER, UserFixture.getUser().getId()))
            .content(json)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller delete user by id")
    void deleteUserShouldReturnSuccessful() throws Exception {
        doNothing().when(this.service).deleteUser(anyString());

        RequestBuilder request = MockMvcRequestBuilders.delete(String.format("%s/%s", PATH_USER, UserFixture.getUser().getId()))
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNoContent());
    }

}
