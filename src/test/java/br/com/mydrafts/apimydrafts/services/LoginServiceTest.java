package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.documents.UserDocument;
import br.com.mydrafts.apimydrafts.dto.LoginDTO;
import br.com.mydrafts.apimydrafts.dto.LoginFormDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.repository.UserRepository;
import br.com.mydrafts.apimydrafts.fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for LoginService")
class LoginServiceTest {

    private LoginService service;

    @Mock
    private JWTService jwtService;

    @Mock
    private UserRepository repository;

    @BeforeEach
    void setup() {
        service = new LoginServiceImpl(repository, jwtService, new ModelMapper());
    }

    @Test
    @DisplayName("Service login user successful")
    void loginUserShouldReturnSuccessful() {
        LoginFormDTO loginForm = new LoginFormDTO("alvaro@email.com", "12345678");
        when(repository.findByEmail(loginForm.getEmail())).thenReturn(Optional.of(UserFixture.getUser()));
        when(jwtService.generateToken(UserFixture.getUserDTO())).thenReturn("token");

        LoginDTO login = service.login(loginForm);

        assertThat(login.getToken()).isNotEmpty();
        assertThat(login.getUser().getFirstName()).isEqualTo(UserFixture.getUser().getFirstName());
    }

    @Test
    @DisplayName("Service login user not found")
    void loginUserShouldReturnUserNotFound() {
        when(repository.findByEmail("alvaro@email.com")).thenReturn(Optional.empty());

        LoginFormDTO loginForm = new LoginFormDTO("alvaro@email.com", "12345678");

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.login(loginForm));
    }

    @Test
    @DisplayName("Service login password incorrect")
    void loginUserShouldReturnPasswordIncorrect() {
        UserDocument user = UserFixture.getUser();
        user.setPassword("12345678");
        when(repository.findByEmail("alvaro@email.com")).thenReturn(Optional.of(user));

        LoginFormDTO loginForm = new LoginFormDTO("alvaro@email.com", "12345678");

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.login(loginForm));
    }

}
