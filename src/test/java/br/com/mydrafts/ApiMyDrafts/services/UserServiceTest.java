package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.constants.Gender;
import br.com.mydrafts.ApiMyDrafts.documents.User;
import br.com.mydrafts.ApiMyDrafts.dto.UserDTO;
import br.com.mydrafts.ApiMyDrafts.dto.UserFormDTO;
import br.com.mydrafts.ApiMyDrafts.exceptions.BusinessException;
import br.com.mydrafts.ApiMyDrafts.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for User Service")
public class UserServiceTest {

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;

    @Test
    @DisplayName("Service save user")
    public void saveUserShouldReturnSuccessful() {
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.save(any())).thenReturn(user());

        UserDTO user = service.saveUser(userForm());

        assertThat(user.getName()).isEqualTo(user().getName());
        assertThat(user.getEmail()).isEqualTo(user().getEmail());
    }

    @Test
    @DisplayName("Service save user email conflict")
    public void saveUserShouldReturnEmailConflict() {
        when(repository.existsByEmail(anyString())).thenReturn(true);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.saveUser(userForm()));
    }

    @Test
    @DisplayName("Service search user")
    public void searchUserShouldReturnSuccessful() {
        when(repository.findById(anyString())).thenReturn(Optional.of(user()));

        UserDTO user = service.searchUser("61586ad5362766670067edd5");

        assertThat(user.getName()).isEqualTo(user().getName());
        assertThat(user.getEmail()).isEqualTo(user().getEmail());
    }

    @Test
    @DisplayName("Service search user not found")
    public void searchUserShouldReturnNotFound() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.searchUser("1"));
    }

    @Test
    @DisplayName("Service update user")
    public void updateUserShouldReturnSuccessful() {
        User user = user();
        when(repository.findById(anyString())).thenReturn(Optional.of(user));
        UserFormDTO userForm = userForm();
        userForm.setName("Alvaro Test");
        user.setName("Alvaro Test");
        when(repository.save(any())).thenReturn(user);

        UserDTO saveUser = service.updateUser("61586ad5362766670067edd5", userForm);

        assertThat(saveUser.getName()).isEqualTo(user.getName());
    }

    @Test
    @DisplayName("Service update user email")
    public void updateUserEmailShouldReturnSuccessful() {
        when(repository.findById(anyString())).thenReturn(Optional.of(user()));
        UserFormDTO userForm = userForm();
        userForm.setEmail("alvarotest@email.com");
        User user = user();
        user.setEmail("alvarotest@email.com");
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.save(any())).thenReturn(user);

        UserDTO saveUser = service.updateUser("61586ad5362766670067edd5", userForm);

        assertThat(saveUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("Service update user email conflict")
    public void updateUserShouldReturnEmailConflict() {
        when(repository.findById(anyString())).thenReturn(Optional.of(user()));
        UserFormDTO userForm = userForm();
        userForm.setEmail("alvarotest@email.com");
        when(repository.existsByEmail(anyString())).thenReturn(true);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.updateUser("61586ad5362766670067edd5", userForm));
    }

    @Test
    @DisplayName("Service update user not found")
    public void updateUserShouldReturnNotFound() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.updateUser("1", userForm()));
    }

    @Test
    @DisplayName("Service delete user")
    public void deleteUserShouldReturnSuccessful() {
        when(repository.findById(anyString())).thenReturn(Optional.of(user()));
        doNothing().when(repository).delete(any());

        service.deleteUser("61586ad5362766670067edd5");

        verify(repository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Service delete user not found")
    public void deleteUserShouldReturnNotFound() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.deleteUser("1"));
    }

    private UserFormDTO userForm() {
        return UserFormDTO.builder()
                .name("Alvaro")
                .gender(Gender.MASCULINO)
                .email("alvaro@email.com")
                .password("12345678")
                .build();
    }

    private User user() {
        User user = new User();
        user.setId("61586ad5362766670067edd5");
        user.setName("Alvaro");
        user.setGender(Gender.MASCULINO);
        user.setEmail("alvaro@email.com");
        user.setPassword("12345678");
        return user;
    }

}
