package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.fixtures.LoginFixture;
import br.com.mydrafts.apimydrafts.services.LoginService;
import br.com.mydrafts.apimydrafts.TestUtils;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Login Controller")
class LoginControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LoginService loginService;

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
        String json = TestUtils.readFileAsString("/json/login.json");
        when(this.loginService.login(any())).thenReturn(LoginFixture.getLogin());

        RequestBuilder request = MockMvcRequestBuilders.post(PATH_LOGIN)
            .content(json)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

}
