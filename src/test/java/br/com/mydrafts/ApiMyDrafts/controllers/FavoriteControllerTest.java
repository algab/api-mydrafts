package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.dto.LoginDTO;
import br.com.mydrafts.ApiMyDrafts.repository.UserRepository;
import br.com.mydrafts.ApiMyDrafts.services.FavoriteService;
import br.com.mydrafts.ApiMyDrafts.utils.FavoriteUtil;
import br.com.mydrafts.ApiMyDrafts.utils.TestUtil;
import br.com.mydrafts.ApiMyDrafts.utils.UserUtil;
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
@DisplayName("Tests for Favorite Controller")
public class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoriteService service;

    @MockBean
    private UserRepository userRepository;

    private String token;

    private static final String PATH_LOGIN = "/v1/login";
    private static final String PATH_FAVORITE = "/v1/favorites";

    @BeforeAll
    public void init() throws Exception {
        String json = TestUtil.readFileAsString("/json/login.json");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(UserUtil.getUser()));

        RequestBuilder request = MockMvcRequestBuilders.post(PATH_LOGIN)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request).andReturn();
        token = new ObjectMapper().readValue(result.getResponse().getContentAsString(), LoginDTO.class).getToken();
    }

    @Test
    @DisplayName("Controller save favorite")
    public void saveFavoriteShouldReturnSuccessful() throws Exception {
        String json = TestUtil.readFileAsString("/json/favoriteRequest.json");
        when(this.service.save(any())).thenReturn(FavoriteUtil.getFavoriteDTO());

        RequestBuilder request = MockMvcRequestBuilders.post(PATH_FAVORITE)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Controller delete favorite")
    public void deleteFavoriteShouldReturnSuccessful() throws Exception {
        doNothing().when(this.service).delete(anyString());

        RequestBuilder request = MockMvcRequestBuilders.delete(String.format("%s/6158fb48b7179927e035ae7c", PATH_FAVORITE))
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNoContent());
    }

}