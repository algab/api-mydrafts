package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.documents.User;
import br.com.mydrafts.ApiMyDrafts.dto.LoginFormDTO;
import br.com.mydrafts.ApiMyDrafts.exceptions.BusinessException;
import br.com.mydrafts.ApiMyDrafts.repository.UserRepository;
import br.com.mydrafts.ApiMyDrafts.utils.UserUtil;
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
public class LoginServiceTest {

    @Autowired
    private LoginService service;

    @MockBean
    private UserRepository repository;

    @Test
    @DisplayName("Service login user not found")
    public void loginUserShouldReturnUserNotFound() {
        User user = UserUtil.getUser();
        user.setPassword("12345678");
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.login(new LoginFormDTO("alvaro@email.com", "12345678")));
    }

    @Test
    @DisplayName("Service login password incorrect")
    public void loginUserShouldReturnPasswordIncorrect() {
        User user = UserUtil.getUser();
        user.setPassword("12345678");
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.login(new LoginFormDTO("alvaro@email.com", "12345678")));
    }

}
