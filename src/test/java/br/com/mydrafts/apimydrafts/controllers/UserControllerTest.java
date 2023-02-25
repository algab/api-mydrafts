package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.dto.UserDTO;
import br.com.mydrafts.apimydrafts.dto.UserFormDTO;
import br.com.mydrafts.apimydrafts.exceptions.handler.RestExceptionHandler;
import br.com.mydrafts.apimydrafts.fixtures.DraftFixture;
import br.com.mydrafts.apimydrafts.fixtures.FavoriteFixture;
import br.com.mydrafts.apimydrafts.services.UserService;
import br.com.mydrafts.apimydrafts.fixtures.UserFixture;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for UserController")
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService service;

    private static final Gson gson = new Gson();

    private static final String PATH_USER = "/v1/users";
    private static final String USER_ID = "61586ad5362766670067edd5";

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
        UserFormDTO form = UserFixture.getUserForm();
        UserDTO user = UserFixture.getUserDTO();
        when(this.service.save(form)).thenReturn(user);

        RequestBuilder request = MockMvcRequestBuilders.post(PATH_USER)
            .content(gson.toJson(form))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(user.getId()))
            .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
            .andExpect(jsonPath("$.lastName").value(user.getLastName()));
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
        UserDTO user = UserFixture.getUserDTO();
        when(this.service.search(USER_ID)).thenReturn(user);

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/%s", PATH_USER, USER_ID));

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(user.getId()))
            .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
            .andExpect(jsonPath("$.lastName").value(user.getLastName()));
    }

    @Test
    @DisplayName("Controller get drafts by user")
    void getDraftsByUserShouldReturnSuccessful() throws Exception {
        when(this.service.getDrafts(PageRequest.of(0, 10), USER_ID))
            .thenReturn(DraftFixture.getPageDraftDTO());

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/%s/drafts", PATH_USER, USER_ID));

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("Controller get favorites by user")
    void getFavoritesByUserShouldReturnSuccessful() throws Exception {
        when(this.service.getFavorites(PageRequest.of(0, 10), USER_ID))
            .thenReturn(FavoriteFixture.getPageFavoriteDTO());

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/%s/favorites", PATH_USER, USER_ID));

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("Controller update user by id")
    void updateUserShouldReturnSuccessful() throws Exception {
        UserFormDTO form = UserFixture.getUserForm();
        UserDTO user = UserFixture.getUserDTO();
        when(this.service.update(USER_ID, form)).thenReturn(user);

        RequestBuilder request = MockMvcRequestBuilders.put(String.format("%s/%s", PATH_USER, USER_ID))
            .content(gson.toJson(form))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(user.getId()))
            .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
            .andExpect(jsonPath("$.lastName").value(user.getLastName()));
    }

    @Test
    @DisplayName("Controller delete user by id")
    void deleteUserShouldReturnSuccessful() throws Exception {
        doNothing().when(this.service).delete(USER_ID);

        RequestBuilder request = MockMvcRequestBuilders.delete(String.format("%s/%s", PATH_USER, USER_ID));

        mockMvc.perform(request).andExpect(status().isNoContent());
    }

}
