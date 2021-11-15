package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Draft;
import br.com.mydrafts.apimydrafts.documents.Favorite;
import br.com.mydrafts.apimydrafts.documents.User;
import br.com.mydrafts.apimydrafts.dto.DraftDTO;
import br.com.mydrafts.apimydrafts.dto.FavoriteDTO;
import br.com.mydrafts.apimydrafts.dto.UserDTO;
import br.com.mydrafts.apimydrafts.dto.UserFormDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.repository.DraftRepository;
import br.com.mydrafts.apimydrafts.repository.FavoriteRepository;
import br.com.mydrafts.apimydrafts.repository.UserRepository;
import br.com.mydrafts.apimydrafts.utils.DraftUtil;
import br.com.mydrafts.apimydrafts.utils.FavoriteUtil;
import br.com.mydrafts.apimydrafts.utils.UserUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for User Service")
class UserServiceTest {

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;

    @MockBean
    private DraftRepository draftRepository;

    @MockBean
    private FavoriteRepository favoriteRepository;

    @Test
    @DisplayName("Service save user")
    void saveUserShouldReturnSuccessful() {
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.save(any())).thenReturn(UserUtil.getUser());

        UserDTO user = service.saveUser(UserUtil.userForm());

        assertThat(user.getName()).isEqualTo(UserUtil.getUser().getName());
        assertThat(user.getEmail()).isEqualTo(UserUtil.getUser().getEmail());
    }

    @Test
    @DisplayName("Service save user email conflict")
    void saveUserShouldReturnEmailConflict() {
        when(repository.existsByEmail(anyString())).thenReturn(true);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.saveUser(UserUtil.userForm()));
    }

    @Test
    @DisplayName("Service search user")
    void searchUserShouldReturnSuccessful() {
        when(repository.findById(anyString())).thenReturn(Optional.of(UserUtil.getUser()));

        UserDTO user = service.searchUser(UserUtil.getUser().getId());

        assertThat(user.getName()).isEqualTo(UserUtil.getUser().getName());
        assertThat(user.getEmail()).isEqualTo(UserUtil.getUser().getEmail());
    }

    @Test
    @DisplayName("Service search user not found")
    void searchUserShouldReturnNotFound() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.searchUser("1"));
    }

    @Test
    @DisplayName("Service get drafts by user")
    void getDraftsShouldReturnSuccessful() {
        when(repository.findById(anyString())).thenReturn(Optional.of(UserUtil.getUser()));
        when(draftRepository.findByUser(any(User.class), any())).thenReturn(pageDraft());

        Page<DraftDTO> page = service.getDrafts(PageRequest.of(0, 10), UserUtil.getUser().getId());

        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getContent().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Service get drafts user not found")
    void getDraftsShouldReturnUserNotFound() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.getDrafts(PageRequest.of(0, 10), UserUtil.getUser().getId()));
    }

    @Test
    @DisplayName("Service get favorites by user")
    void getFavoritesShouldReturnSuccessful() {
        when(repository.findById(anyString())).thenReturn(Optional.of(UserUtil.getUser()));
        when(favoriteRepository.findByUser(any(), any())).thenReturn(pageFavorite());

        Page<FavoriteDTO> page = service.getFavorites(PageRequest.of(0, 10), UserUtil.getUser().getId());

        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getContent().get(0).getProduction().getMedia()).isEqualTo(Media.MOVIE);
    }

    @Test
    @DisplayName("Service get drafts user not found")
    void getFavoritesShouldReturnUserNotFound() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.getFavorites(PageRequest.of(0, 10), UserUtil.getUser().getId()));
    }

    @Test
    @DisplayName("Service update user")
    void updateUserShouldReturnSuccessful() {
        User user = UserUtil.getUser();
        when(repository.findById(anyString())).thenReturn(Optional.of(user));
        UserFormDTO userForm = UserUtil.userForm();
        userForm.setName("Alvaro Test");
        user.setName("Alvaro Test");
        when(repository.save(any())).thenReturn(user);

        UserDTO saveUser = service.updateUser(UserUtil.getUser().getId(), userForm);

        assertThat(saveUser.getName()).isEqualTo(user.getName());
    }

    @Test
    @DisplayName("Service update user email")
    void updateUserEmailShouldReturnSuccessful() {
        when(repository.findById(anyString())).thenReturn(Optional.of(UserUtil.getUser()));
        UserFormDTO userForm = UserUtil.userForm();
        userForm.setEmail("alvarotest@email.com");
        User user = UserUtil.getUser();
        user.setEmail("alvarotest@email.com");
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.save(any())).thenReturn(user);

        UserDTO saveUser = service.updateUser(UserUtil.getUser().getId(), userForm);

        assertThat(saveUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("Service update user email conflict")
    void updateUserShouldReturnEmailConflict() {
        when(repository.findById(anyString())).thenReturn(Optional.of(UserUtil.getUser()));
        UserFormDTO userForm = UserUtil.userForm();
        userForm.setEmail("alvarotest@email.com");
        when(repository.existsByEmail(anyString())).thenReturn(true);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.updateUser(UserUtil.getUser().getId(), userForm));
    }

    @Test
    @DisplayName("Service update user not found")
    void updateUserShouldReturnNotFound() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.updateUser("1", UserUtil.userForm()));
    }

    @Test
    @DisplayName("Service delete user")
    void deleteUserShouldReturnSuccessful() {
        when(repository.findById(anyString())).thenReturn(Optional.of(UserUtil.getUser()));
        doNothing().when(repository).delete(any());

        service.deleteUser(UserUtil.getUser().getId());

        verify(repository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Service delete user not found")
    void deleteUserShouldReturnNotFound() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.deleteUser("1"));
    }

    private Page<Draft> pageDraft() {
        return new PageImpl<>(Arrays.asList(DraftUtil.getDraft(Media.MOVIE)), PageRequest.of(0, 10), 1);
    }

    private Page<Favorite> pageFavorite() {
        return new PageImpl<>(Arrays.asList(FavoriteUtil.getFavorite(Media.MOVIE)), PageRequest.of(0, 10), 1);
    }

}
