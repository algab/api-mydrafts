package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.dto.LoginDTO;
import br.com.mydrafts.apimydrafts.repository.UserRepository;
import br.com.mydrafts.apimydrafts.services.UserService;
import br.com.mydrafts.apimydrafts.builder.DraftBuilder;
import br.com.mydrafts.apimydrafts.builder.FavoriteBuilder;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Tests for User Controller")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @MockBean
    private UserRepository repository;

    private String token;

    private static final String PATH_LOGIN = "/v1/login";
    private static final String PATH_USER = "/v1/users";

    @BeforeAll
    public void init() throws Exception {
        String json = TestUtil.readFileAsString("/json/login.json");
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(UserBuilder.getUser()));

        RequestBuilder request = MockMvcRequestBuilders.post(PATH_LOGIN)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request).andReturn();
        token = new ObjectMapper().readValue(result.getResponse().getContentAsString(), LoginDTO.class).getToken();
    }

    @Test
    @DisplayName("Controller save user")
    void saveUserShouldReturnSuccessful() throws Exception {
        String json = TestUtil.readFileAsString("/json/userRequest.json");
        when(this.service.saveUser(any())).thenReturn(UserBuilder.getUserDTO());

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
        when(this.service.saveUser(any())).thenReturn(UserBuilder.getUserDTO());

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
        when(this.service.searchUser(anyString())).thenReturn(UserBuilder.getUserDTO());

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/%s", PATH_USER, UserBuilder.getUser().getId()))
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller search user unauthorized")
    void searchUserShouldReturnUnauthorized() throws Exception {
        String json = TestUtil.readFileAsString("/json/user.json");
        when(this.service.searchUser(anyString())).thenReturn(UserBuilder.getUserDTO());

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/%s", PATH_USER, UserBuilder.getUser().getId()))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Controller search user token unauthorized")
    void searchUserShouldReturnTokenUnauthorized() throws Exception {
        String json = TestUtil.readFileAsString("/json/user.json");
        when(this.service.searchUser(anyString())).thenReturn(UserBuilder.getUserDTO());

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/1", PATH_USER))
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Controller search user token exception")
    void searchUserShouldReturnTokenException() throws Exception {
        String json = TestUtil.readFileAsString("/json/user.json");
        when(this.service.searchUser(anyString())).thenReturn(UserBuilder.getUserDTO());

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/1", PATH_USER))
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", "eyJhbGciOiJIUzI1NiJ9"))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Controller get drafts by user")
    void getDraftsByUserShouldReturnSuccessful() throws Exception {
        String json = TestUtil.readFileAsString("/json/draftsUser.json");
        when(this.service.getDrafts(any(), anyString())).thenReturn(new PageImpl<>(Arrays.asList(DraftBuilder.getDraftDTO()), PageRequest.of(0, 10), 1));

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/%s/drafts", PATH_USER, UserBuilder.getUser().getId()))
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller get favorites by user")
    void getFavoritesByUserShouldReturnSuccessful() throws Exception {
        String json = TestUtil.readFileAsString("/json/favoritesUser.json");
        when(this.service.getFavorites(any(), anyString())).thenReturn(new PageImpl<>(Arrays.asList(FavoriteBuilder.getFavoriteDTO()), PageRequest.of(0, 10), 1));

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/%s/favorites", PATH_USER, UserBuilder.getUser().getId()))
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller update user by id")
    void updateUserShouldReturnSuccessful() throws Exception {
        String json = TestUtil.readFileAsString("/json/userRequest.json");
        when(this.service.updateUser(anyString(), any())).thenReturn(UserBuilder.getUserDTO());

        RequestBuilder request = MockMvcRequestBuilders.put(String.format("%s/%s", PATH_USER, UserBuilder.getUser().getId()))
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller delete user by id")
    void deleteUserShouldReturnSuccessful() throws Exception {
        doNothing().when(this.service).deleteUser(anyString());

        RequestBuilder request = MockMvcRequestBuilders.delete(String.format("%s/%s", PATH_USER, UserBuilder.getUser().getId()))
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNoContent());
    }

}
