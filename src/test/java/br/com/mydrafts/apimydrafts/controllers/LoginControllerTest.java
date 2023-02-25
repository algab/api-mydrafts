package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.dto.LoginFormDTO;
import br.com.mydrafts.apimydrafts.fixtures.LoginFixture;
import br.com.mydrafts.apimydrafts.services.LoginService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for LoginController")
class LoginControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LoginService loginService;

    private static final Gson gson = new Gson();

    private static final String PATH_LOGIN = "/v1/login";

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(new LoginController(loginService))
            .build();
    }

    @Test
    @DisplayName("Controller login")
    void loginUserShouldReturnSuccessful() throws Exception {
        LoginFormDTO form = LoginFixture.getLoginForm();
        when(this.loginService.login(form)).thenReturn(LoginFixture.getLogin());

        RequestBuilder request = MockMvcRequestBuilders.post(PATH_LOGIN)
            .content(gson.toJson(form))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.user").isNotEmpty());
    }

}
