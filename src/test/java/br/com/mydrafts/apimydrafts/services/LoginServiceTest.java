package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.documents.User;
import br.com.mydrafts.apimydrafts.dto.LoginFormDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.repository.UserRepository;
import br.com.mydrafts.apimydrafts.builder.UserBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Login Service")
class LoginServiceTest {

    @Autowired
    private LoginService service;

    @MockBean
    private UserRepository repository;

    @Test
    @DisplayName("Service login user not found")
    void loginUserShouldReturnUserNotFound() {
        User user = UserBuilder.getUser();
        user.setPassword("12345678");
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.login(new LoginFormDTO("alvaro@email.com", "12345678")));
    }

    @Test
    @DisplayName("Service login password incorrect")
    void loginUserShouldReturnPasswordIncorrect() {
        User user = UserBuilder.getUser();
        user.setPassword("12345678");
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.login(new LoginFormDTO("alvaro@email.com", "12345678")));
    }

}
