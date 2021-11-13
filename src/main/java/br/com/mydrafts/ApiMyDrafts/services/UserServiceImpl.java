package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.documents.Draft;
import br.com.mydrafts.ApiMyDrafts.documents.Favorite;
import br.com.mydrafts.ApiMyDrafts.documents.User;
import br.com.mydrafts.ApiMyDrafts.dto.DraftDTO;
import br.com.mydrafts.ApiMyDrafts.dto.FavoriteDTO;
import br.com.mydrafts.ApiMyDrafts.dto.UserDTO;
import br.com.mydrafts.ApiMyDrafts.dto.UserFormDTO;
import br.com.mydrafts.ApiMyDrafts.exceptions.BusinessException;
import br.com.mydrafts.ApiMyDrafts.repository.DraftRepository;
import br.com.mydrafts.ApiMyDrafts.repository.FavoriteRepository;
import br.com.mydrafts.ApiMyDrafts.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private DraftRepository draftRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ModelMapper mapper;

    private static final String CONFLICT = "CONFLICT";
    private static final String NOT_FOUND = "NOT FOUND";
    private static final Integer STATUS_CONFLICT = 409;
    private static final Integer STATUS_NOT_FOUND = 404;
    private static final String MESSAGE_EMAIL_CONFLICT = "Email is conflict";
    private static final String MESSAGE_USER_NOT_FOUND = "User not found";

    @Override
    public UserDTO saveUser(UserFormDTO body) {
        log.info("UserServiceImpl.saveUser - Start - Input: name {}, email {}", body.getName(), body.getEmail());
        if (!this.repository.existsByEmail(body.getEmail())) {
            body.setPassword(new BCryptPasswordEncoder().encode(body.getPassword()));
            User user = this.repository.save(mapper.map(body, User.class));
            UserDTO userResult = mapper.map(user, UserDTO.class);
            log.info("UserServiceImpl.saveUser - End - Input: name {}, email {} - Output: {}", body.getName(), body.getEmail(), userResult);
            return userResult;
        } else {
            log.error("UserServiceImpl.saveUser - Error: {}", MESSAGE_EMAIL_CONFLICT);
            throw new BusinessException(STATUS_CONFLICT, CONFLICT, MESSAGE_EMAIL_CONFLICT);
        }
    }

    @Override
    public UserDTO searchUser(String id) {
        log.info("UserServiceImpl.searchUser - Start - Input: id {}", id);

        User user = this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("UserServiceImpl.searchUser - Error: {}", MESSAGE_USER_NOT_FOUND);
                    return new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_USER_NOT_FOUND);
                });

        UserDTO userResult = mapper.map(user, UserDTO.class);
        log.info("UserServiceImpl.searchUser - End - Input: id {} - Output: {}", id, userResult);
        return userResult;
    }

    @Override
    public Page<DraftDTO> getDrafts(Pageable page, String id) {
        log.info("UserServiceImpl.getDrafts - Start - Input: id {}, page {}", id, page);

        User user = this.repository.findById(id)
                .orElseThrow(() -> new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_USER_NOT_FOUND));
        Page<Draft> drafts = this.draftRepository.findByUser(user, page);
        List<DraftDTO> draftsDTO = drafts.getContent().stream()
                .map(draft -> mapper.map(draft, DraftDTO.class))
                .collect(Collectors.toList());

        Page<DraftDTO> pageDraft = new PageImpl<>(draftsDTO, page, drafts.getTotalElements());
        log.info("UserServiceImpl.getDrafts - End - Input: id {}, page {} - Output: {}", id, page, pageDraft);
        return pageDraft;
    }

    @Override
    public Page<FavoriteDTO> getFavorites(Pageable page, String id) {
        log.info("UserServiceImpl.getFavorites - Start - Input: id {}, page {}", id, page);

        User user = this.repository.findById(id)
                .orElseThrow(() -> new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_USER_NOT_FOUND));
        Page<Favorite> favorites = this.favoriteRepository.findByUser(user, page);
        List<FavoriteDTO> content = favorites.getContent().stream()
                .map(favorite -> mapper.map(favorite, FavoriteDTO.class))
                .collect(Collectors.toList());

        Page<FavoriteDTO> pageFavorite = new PageImpl<>(content, page, favorites.getTotalElements());
        log.info("UserServiceImpl.getFavorites - End - Input: id {}, page {} - Output: {}", id, page, pageFavorite);
        return pageFavorite;
    }

    @Override
    public UserDTO updateUser(String id, UserFormDTO body) {
        log.info("UserServiceImpl.updateUser - Start - Input: id {}", id);

        User user = this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("UserServiceImpl.updateUser - Error: {}", MESSAGE_USER_NOT_FOUND);
                    return new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_USER_NOT_FOUND);
                });
        user.setName(body.getName());
        user.setGender(body.getGender());
        if (user.getEmail().equals(body.getEmail())) {
            user.setEmail(body.getEmail());
        } else {
            if (!this.repository.existsByEmail(body.getEmail())) {
                user.setEmail(body.getEmail());
            } else {
                log.error("UserServiceImpl.updateUser - Error: {}", MESSAGE_EMAIL_CONFLICT);
                throw new BusinessException(STATUS_CONFLICT, CONFLICT, MESSAGE_EMAIL_CONFLICT);
            }
        }

        UserDTO userResult = mapper.map(this.repository.save(user), UserDTO.class);
        log.info("UserServiceImpl.updateUser - End - Input: id {} - Output: {}", id, userResult);
        return userResult;
    }

    @Override
    public void deleteUser(String id) {
        log.info("UserServiceImpl.deleteUser - Start - Input: id {}", id);

        User user = this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("UserServiceImpl.updateUser - Error: {}", MESSAGE_USER_NOT_FOUND);
                    return new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_USER_NOT_FOUND);
                });

        log.info("UserServiceImpl.deleteUser - End - Input: id {}", id);
        this.repository.delete(user);
    }

}
