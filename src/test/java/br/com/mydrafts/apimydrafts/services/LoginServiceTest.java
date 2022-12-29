package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.documents.User;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Login Service")
class LoginServiceTest {

    private LoginService service;

    @Mock
    private UserRepository repository;

    @BeforeEach
    void setup() {
        service = new LoginServiceImpl(repository, new ModelMapper());
        ReflectionTestUtils.setField(service, "secret", "aMH%Q#LHJL@oCWZko@x)+cYk,r:}kHz@T6rlj4st");
    }

    @Test
    @DisplayName("Service login user successful")
    void loginUserShouldReturnSuccessful() {
        LoginFormDTO loginForm = new LoginFormDTO("alvaro@email.com", "12345678");
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(UserFixture.getUser()));

        LoginDTO login = service.login(loginForm);

        assertThat(login.getToken()).isNotEmpty();
        assertThat(login.getUser().getName()).isEqualTo(UserFixture.getUser().getName());
    }

    @Test
    @DisplayName("Service login user not found")
    void loginUserShouldReturnUserNotFound() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        LoginFormDTO loginForm = new LoginFormDTO("alvaro@email.com", "12345678");

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.login(loginForm));
    }

    @Test
    @DisplayName("Service login password incorrect")
    void loginUserShouldReturnPasswordIncorrect() {
        User user = UserFixture.getUser();
        user.setPassword("12345678");
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));

        LoginFormDTO loginForm = new LoginFormDTO("alvaro@email.com", "12345678");

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.login(loginForm));
    }

}
