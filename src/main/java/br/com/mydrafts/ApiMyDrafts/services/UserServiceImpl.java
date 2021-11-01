package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.documents.User;
import br.com.mydrafts.ApiMyDrafts.dto.UserDTO;
import br.com.mydrafts.ApiMyDrafts.dto.UserFormDTO;
import br.com.mydrafts.ApiMyDrafts.exceptions.BusinessException;
import br.com.mydrafts.ApiMyDrafts.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

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
        if (!this.repository.existsByEmail(body.getEmail())) {
            body.setPassword(new BCryptPasswordEncoder().encode(body.getPassword()));
            User user = this.repository.save(mapper.map(body, User.class));
            return mapper.map(user, UserDTO.class);
        } else {
            throw new BusinessException(STATUS_CONFLICT, CONFLICT, MESSAGE_EMAIL_CONFLICT);
        }
    }

    @Override
    public UserDTO searchUser(String id) {
        User user = this.repository.findById(id)
                .orElseThrow(() -> new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_USER_NOT_FOUND));
        return mapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(String id, UserFormDTO body) {
        User user = this.repository.findById(id)
                .orElseThrow(() -> new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_USER_NOT_FOUND));
        user.setName(body.getName());
        user.setGender(body.getGender());
        if (user.getEmail().equals(body.getEmail())) {
            user.setEmail(body.getEmail());
        } else {
            if (!this.repository.existsByEmail(body.getEmail())) {
                user.setEmail(body.getEmail());
            } else {
                throw new BusinessException(STATUS_CONFLICT, CONFLICT, MESSAGE_EMAIL_CONFLICT);
            }
        }
        return mapper.map(this.repository.save(user), UserDTO.class);
    }

    @Override
    public void deleteUser(String id) {
        User user = this.repository.findById(id)
                .orElseThrow(() -> new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_USER_NOT_FOUND));
        this.repository.delete(user);
    }

}
