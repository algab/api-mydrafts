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
    private ModelMapper modelMapper;

    private static final Integer statusConflict = 409;
    private static final Integer statusNotFound = 404;
    private static final String conflict = "CONFLICT";
    private static final String notFound = "NOT FOUND";
    private static final String messageConflict = "Email is conflict";
    private static final String messageNotFound = "User not found";

    @Override
    public UserDTO saveUser(UserFormDTO body) {
        if (!this.repository.existsByEmail(body.getEmail())) {
            body.setPassword(new BCryptPasswordEncoder().encode(body.getPassword()));
            User user = this.repository.save(modelMapper.map(body, User.class));
            return modelMapper.map(user, UserDTO.class);
        } else {
            throw new BusinessException(statusConflict, conflict, messageConflict);
        }
    }

    @Override
    public UserDTO searchUser(String id) {
        User user = this.repository.findById(id)
                .orElseThrow(() -> new BusinessException(statusNotFound, notFound, messageNotFound));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(String id, UserFormDTO body) {
        User user = this.repository.findById(id)
                .orElseThrow(() -> new BusinessException(statusNotFound, notFound, messageNotFound));
        user.setName(body.getName());
        user.setGender(body.getGender());
        if (user.getEmail().equals(body.getEmail())) {
            user.setEmail(body.getEmail());
        } else {
            if (!this.repository.existsByEmail(body.getEmail())) {
                user.setEmail(body.getEmail());
            } else {
                throw new BusinessException(statusConflict, conflict, messageConflict);
            }
        }
        return modelMapper.map(this.repository.save(user), UserDTO.class);
    }

    @Override
    public void deleteUser(String id) {
        User user = this.repository.findById(id)
                .orElseThrow(() -> new BusinessException(statusNotFound, notFound, messageNotFound));
        this.repository.delete(user);
    }

}
