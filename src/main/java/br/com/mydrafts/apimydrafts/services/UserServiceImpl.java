package br.com.mydrafts.apimydrafts.services;

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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.mydrafts.apimydrafts.constants.MyDraftsMessage.EMAIL_CONFLICT;
import static br.com.mydrafts.apimydrafts.constants.MyDraftsMessage.USER_NOT_FOUND;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    private DraftRepository draftRepository;

    private FavoriteRepository favoriteRepository;

    private ModelMapper mapper;

    @Override
    public UserDTO save(UserFormDTO body) {
        if (!this.repository.existsByEmail(body.getEmail())) {
            body.setPassword(new BCryptPasswordEncoder().encode(body.getPassword()));
            UserDTO user = mapper.map(this.repository.save(mapper.map(body, User.class)), UserDTO.class);
            log.info("UserServiceImpl.saveUser - User saved - user: [{}]", user);
            return user;
        } else {
            log.error("UserServiceImpl.saveUser - Error: {}", EMAIL_CONFLICT);
            throw new BusinessException(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                EMAIL_CONFLICT
            );
        }
    }

    @Override
    public UserDTO search(String id) {
        User user = this.repository.findById(id)
            .orElseThrow(() -> {
                log.error("UserServiceImpl.searchUser - Error: {}", USER_NOT_FOUND);
                return new BusinessException(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    USER_NOT_FOUND
                );
            });
        return mapper.map(user, UserDTO.class);
    }

    @Override
    public Page<DraftDTO> getDrafts(Pageable page, String id) {
        User user = this.repository.findById(id)
            .orElseThrow(() -> new BusinessException(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                USER_NOT_FOUND
            ));
        Page<Draft> drafts = this.draftRepository.findByUser(user, page);
        List<DraftDTO> draftsDTO = drafts.getContent().stream()
            .map(draft -> mapper.map(draft, DraftDTO.class))
            .collect(Collectors.toList());
        return new PageImpl<>(draftsDTO, page, drafts.getTotalElements());
    }

    @Override
    public Page<FavoriteDTO> getFavorites(Pageable page, String id) {
        User user = this.repository.findById(id)
            .orElseThrow(() -> new BusinessException(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                USER_NOT_FOUND
            ));
        Page<Favorite> favorites = this.favoriteRepository.findByUser(user, page);
        List<FavoriteDTO> content = favorites.getContent().stream()
            .map(favorite -> mapper.map(favorite, FavoriteDTO.class))
            .collect(Collectors.toList());
        return new PageImpl<>(content, page, favorites.getTotalElements());
    }

    @Override
    public UserDTO update(String id, UserFormDTO body) {
        User user = this.repository.findById(id)
            .orElseThrow(() -> {
                log.error("UserServiceImpl.updateUser - Error: {}", USER_NOT_FOUND);
                return new BusinessException(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    USER_NOT_FOUND
                );
            });
        user.setFirstName(body.getFirstName());
        user.setLastName(body.getLastName());
        user.setGender(body.getGender());
        if (user.getEmail().equals(body.getEmail())) {
            user.setEmail(body.getEmail());
        } else {
            if (!this.repository.existsByEmail(body.getEmail())) {
                user.setEmail(body.getEmail());
            } else {
                log.error("UserServiceImpl.updateUser - Error: {}", EMAIL_CONFLICT);
                throw new BusinessException(
                    HttpStatus.CONFLICT.value(),
                    HttpStatus.CONFLICT.getReasonPhrase(),
                    EMAIL_CONFLICT
                );
            }
        }
        UserDTO userUpdated = mapper.map(this.repository.save(user), UserDTO.class);
        log.info("UserServiceImpl.updateUser - User updated - user: [{}]", userUpdated);
        return userUpdated;
    }

    @Override
    public void delete(String id) {
        User user = this.repository.findById(id)
            .orElseThrow(() -> {
                log.error("UserServiceImpl.deleteUser - Error: {}", USER_NOT_FOUND);
                return new BusinessException(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    USER_NOT_FOUND
                );
            });
        log.info("UserServiceImpl.deleteUser - User removed - id: [{}]", id);
        this.repository.delete(user);
    }

}
