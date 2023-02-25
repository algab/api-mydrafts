package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.UserDocument;
import br.com.mydrafts.apimydrafts.dto.DraftDTO;
import br.com.mydrafts.apimydrafts.dto.FavoriteDTO;
import br.com.mydrafts.apimydrafts.dto.UserDTO;
import br.com.mydrafts.apimydrafts.dto.UserFormDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.fixtures.DraftFixture;
import br.com.mydrafts.apimydrafts.fixtures.FavoriteFixture;
import br.com.mydrafts.apimydrafts.repository.DraftRepository;
import br.com.mydrafts.apimydrafts.repository.FavoriteRepository;
import br.com.mydrafts.apimydrafts.repository.UserRepository;
import br.com.mydrafts.apimydrafts.fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for UserService")
class UserServiceTest {

    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private DraftRepository draftRepository;

    @Mock
    private FavoriteRepository favoriteRepository;

    @BeforeEach
    void setup() {
        service = new UserServiceImpl(repository, draftRepository, favoriteRepository, new ModelMapper());
    }

    @Test
    @DisplayName("Service save user")
    void saveUserShouldReturnSuccessful() {
        UserFormDTO form = UserFixture.getUserForm();
        UserDocument document = UserFixture.getUser();
        when(repository.existsByEmail(form.getEmail())).thenReturn(false);
        when(repository.save(any(UserDocument.class))).thenReturn(document);

        UserDTO user = service.save(form);

        assertThat(user.getFirstName()).isEqualTo(document.getFirstName());
        assertThat(user.getEmail()).isEqualTo(document.getEmail());
    }

    @Test
    @DisplayName("Service save user email conflict")
    void saveUserShouldReturnEmailConflict() {
        UserFormDTO form = UserFixture.getUserForm();
        when(repository.existsByEmail(form.getEmail())).thenReturn(true);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(form));
    }

    @Test
    @DisplayName("Service search user")
    void searchUserShouldReturnSuccessful() {
        UserDocument document = UserFixture.getUser();
        when(repository.findById(document.getId())).thenReturn(Optional.of(document));

        UserDTO user = service.search(document.getId());

        assertThat(user.getFirstName()).isEqualTo(document.getFirstName());
        assertThat(user.getEmail()).isEqualTo(document.getEmail());
    }

    @Test
    @DisplayName("Service search user not found")
    void searchUserShouldReturnNotFound() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.search("1"));
    }

    @Test
    @DisplayName("Service get drafts by user")
    void getDraftsShouldReturnSuccessful() {
        UserDocument document = UserFixture.getUser();
        when(repository.findById(document.getId())).thenReturn(Optional.of(document));
        when(draftRepository.findByUser(any(UserDocument.class), any())).thenReturn(DraftFixture.getPageDraftDocument());

        Page<DraftDTO> page = service.getDrafts(PageRequest.of(0, 10), document.getId());

        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("Service get drafts user not found")
    void getDraftsShouldReturnUserNotFound() {
        String id = UserFixture.getUser().getId();
        PageRequest page = PageRequest.of(0, 10);
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.getDrafts(page, id));
    }

    @Test
    @DisplayName("Service get favorites by user")
    void getFavoritesShouldReturnSuccessful() {
        UserDocument document = UserFixture.getUser();
        when(repository.findById(document.getId())).thenReturn(Optional.of(document));
        when(favoriteRepository.findByUser(any(UserDocument.class), any())).thenReturn(FavoriteFixture.getPageFavoriteDocument());

        Page<FavoriteDTO> page = service.getFavorites(PageRequest.of(0, 10), document.getId());

        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getContent().get(0).getProduction().getMedia()).isEqualTo(Media.MOVIE);
    }

    @Test
    @DisplayName("Service get drafts user not found")
    void getFavoritesShouldReturnUserNotFound() {
        String id = UserFixture.getUser().getId();
        PageRequest page = PageRequest.of(0, 10);
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.getFavorites(page, id));
    }

    @Test
    @DisplayName("Service update user")
    void updateUserShouldReturnSuccessful() {
        UserDocument user = UserFixture.getUser();
        when(repository.findById(anyString())).thenReturn(Optional.of(user));
        UserFormDTO userForm = UserFixture.getUserForm();
        userForm.setFirstName("User");
        userForm.setLastName("Test");
        user.setFirstName("User");
        user.setLastName("Test");
        when(repository.save(any(UserDocument.class))).thenReturn(user);

        UserDTO userDocument = service.update(UserFixture.getUser().getId(), userForm);

        assertThat(userDocument.getFirstName()).isEqualTo(user.getFirstName());
    }

    @Test
    @DisplayName("Service update user email")
    void updateUserEmailShouldReturnSuccessful() {
        when(repository.findById(anyString())).thenReturn(Optional.of(UserFixture.getUser()));
        UserFormDTO userForm = UserFixture.getUserForm();
        userForm.setEmail("alvarotest@email.com");
        UserDocument user = UserFixture.getUser();
        user.setEmail("alvarotest@email.com");
        when(repository.existsByEmail("alvarotest@email.com")).thenReturn(false);
        when(repository.save(any(UserDocument.class))).thenReturn(user);

        UserDTO userDocument = service.update(UserFixture.getUser().getId(), userForm);

        assertThat(userDocument.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("Service update user email conflict")
    void updateUserShouldReturnEmailConflict() {
        when(repository.findById(anyString())).thenReturn(Optional.of(UserFixture.getUser()));
        UserFormDTO userForm = UserFixture.getUserForm();
        userForm.setEmail("alvarotest@email.com");
        when(repository.existsByEmail(anyString())).thenReturn(true);

        String id = UserFixture.getUser().getId();

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.update(id, userForm));
    }

    @Test
    @DisplayName("Service update user not found")
    void updateUserShouldReturnNotFound() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        UserFormDTO form = UserFixture.getUserForm();

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.update("1", form));
    }

    @Test
    @DisplayName("Service delete user")
    void deleteUserShouldReturnSuccessful() {
        UserDocument user = UserFixture.getUser();
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        doNothing().when(repository).delete(user);

        service.delete(user.getId());

        verify(repository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Service delete user not found")
    void deleteUserShouldReturnNotFound() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.delete("1"));
    }

}
