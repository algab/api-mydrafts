package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.DraftDocument;
import br.com.mydrafts.apimydrafts.documents.FavoriteDocument;
import br.com.mydrafts.apimydrafts.documents.ProductionDocument;
import br.com.mydrafts.apimydrafts.documents.UserDocument;
import br.com.mydrafts.apimydrafts.dto.DraftDTO;
import br.com.mydrafts.apimydrafts.dto.FavoriteDTO;
import br.com.mydrafts.apimydrafts.dto.UserDTO;
import br.com.mydrafts.apimydrafts.dto.UserFormDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.fixtures.ProductionFixture;
import br.com.mydrafts.apimydrafts.repository.DraftRepository;
import br.com.mydrafts.apimydrafts.repository.FavoriteRepository;
import br.com.mydrafts.apimydrafts.repository.UserRepository;
import br.com.mydrafts.apimydrafts.fixtures.DraftFixture;
import br.com.mydrafts.apimydrafts.fixtures.FavoriteFixture;
import br.com.mydrafts.apimydrafts.fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for User Service")
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
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.save(any())).thenReturn(UserFixture.getUser());

        UserDTO user = service.save(UserFixture.getUserForm());

        assertThat(user.getFirstName()).isEqualTo(UserFixture.getUser().getFirstName());
        assertThat(user.getEmail()).isEqualTo(UserFixture.getUser().getEmail());
    }

    @Test
    @DisplayName("Service save user email conflict")
    void saveUserShouldReturnEmailConflict() {
        when(repository.existsByEmail(anyString())).thenReturn(true);

        UserFormDTO userForm = UserFixture.getUserForm();

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.save(userForm));
    }

    @Test
    @DisplayName("Service search user")
    void searchUserShouldReturnSuccessful() {
        when(repository.findById(anyString())).thenReturn(Optional.of(UserFixture.getUser()));

        UserDTO user = service.search(UserFixture.getUser().getId());

        assertThat(user.getFirstName()).isEqualTo(UserFixture.getUser().getFirstName());
        assertThat(user.getEmail()).isEqualTo(UserFixture.getUser().getEmail());
    }

    @Test
    @DisplayName("Service search user not found")
    void searchUserShouldReturnNotFound() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.search("1"));
    }

    @Test
    @DisplayName("Service get drafts by user")
    void getDraftsShouldReturnSuccessful() {
        when(repository.findById(anyString())).thenReturn(Optional.of(UserFixture.getUser()));
        when(draftRepository.findByUser(any(UserDocument.class), any())).thenReturn(pageDraft());

        Page<DraftDTO> page = service.getDrafts(PageRequest.of(0, 10), UserFixture.getUser().getId());

        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("Service get drafts user not found")
    void getDraftsShouldReturnUserNotFound() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        String id = UserFixture.getUser().getId();
        PageRequest page = PageRequest.of(0, 10);

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.getDrafts(page, id));
    }

    @Test
    @DisplayName("Service get favorites by user")
    void getFavoritesShouldReturnSuccessful() {
        when(repository.findById(anyString())).thenReturn(Optional.of(UserFixture.getUser()));
        when(favoriteRepository.findByUser(any(), any())).thenReturn(pageFavorite());

        Page<FavoriteDTO> page = service.getFavorites(PageRequest.of(0, 10), UserFixture.getUser().getId());

        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getContent().get(0).getProduction().getMedia()).isEqualTo(Media.MOVIE);
    }

    @Test
    @DisplayName("Service get drafts user not found")
    void getFavoritesShouldReturnUserNotFound() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        String id = UserFixture.getUser().getId();
        PageRequest page = PageRequest.of(0, 10);

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
        when(repository.save(any())).thenReturn(user);

        UserDTO saveUser = service.update(UserFixture.getUser().getId(), userForm);

        assertThat(saveUser.getFirstName()).isEqualTo(user.getFirstName());
    }

    @Test
    @DisplayName("Service update user email")
    void updateUserEmailShouldReturnSuccessful() {
        when(repository.findById(anyString())).thenReturn(Optional.of(UserFixture.getUser()));
        UserFormDTO userForm = UserFixture.getUserForm();
        userForm.setEmail("alvarotest@email.com");
        UserDocument user = UserFixture.getUser();
        user.setEmail("alvarotest@email.com");
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.save(any())).thenReturn(user);

        UserDTO saveUser = service.update(UserFixture.getUser().getId(), userForm);

        assertThat(saveUser.getEmail()).isEqualTo(user.getEmail());
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
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        UserFormDTO userForm = UserFixture.getUserForm();

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.update("1", userForm));
    }

    @Test
    @DisplayName("Service delete user")
    void deleteUserShouldReturnSuccessful() {
        when(repository.findById(anyString())).thenReturn(Optional.of(UserFixture.getUser()));
        doNothing().when(repository).delete(any());

        service.delete(UserFixture.getUser().getId());

        verify(repository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Service delete user not found")
    void deleteUserShouldReturnNotFound() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> service.delete("1"));
    }

    private Page<DraftDocument> pageDraft() {
        ProductionDocument production = ProductionFixture.getProductionMovie();
        return new PageImpl<>(
            Collections.singletonList(DraftFixture.getDraft(production)),
            PageRequest.of(0, 10),
            1
        );
    }

    private Page<FavoriteDocument> pageFavorite() {
        return new PageImpl<>(
            Collections.singletonList(FavoriteFixture.getFavorite()),
            PageRequest.of(0, 10),
            1
        );
    }

}
