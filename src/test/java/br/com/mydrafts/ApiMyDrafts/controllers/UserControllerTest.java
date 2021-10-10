package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.constants.Gender;
import br.com.mydrafts.ApiMyDrafts.dto.UserDTO;
import br.com.mydrafts.ApiMyDrafts.services.UserService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for User Controller")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    private static final String uriUser = "/v1/users";

    @Test
    @DisplayName("Controller save user")
    public void saveUserShouldReturnSuccessful() throws Exception {
        String json = readFileAsString("/json/userRequest.json");
        when(this.service.saveUser(any())).thenReturn(user());

        RequestBuilder request = MockMvcRequestBuilders.post(uriUser)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Controller save user bad request")
    public void saveUserShouldReturnBadRequest() throws Exception {
        String json = readFileAsString("/json/userBadRequest.json");
        when(this.service.saveUser(any())).thenReturn(user());

        RequestBuilder request = MockMvcRequestBuilders.post(uriUser)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Controller search user by id")
    public void searchUserShouldReturnSuccessful() throws Exception {
        String json = readFileAsString("/json/user.json");
        when(this.service.searchUser(anyString())).thenReturn(user());

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/1", uriUser))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller update user by id")
    public void updateUserShouldReturnSuccessful() throws Exception {
        String json = readFileAsString("/json/userRequest.json");
        when(this.service.updateUser(anyString(), any())).thenReturn(user());

        RequestBuilder request = MockMvcRequestBuilders.put(String.format("%s/1", uriUser))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Controller delete user by id")
    public void deleteUserShouldReturnSuccessful() throws Exception {
        doNothing().when(this.service).deleteUser(anyString());

        RequestBuilder request = MockMvcRequestBuilders.delete(String.format("%s/1", uriUser)).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNoContent());
    }

    private UserDTO user() {
        return UserDTO.builder()
                .id("1")
                .name("Alvaro Oliveira")
                .email("alvaro@email.com")
                .gender(Gender.MASCULINO)
                .build();
    }

    private static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(String.format("src/test/resources%s", file))));
    }

}
