package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.documents.User;
import br.com.mydrafts.ApiMyDrafts.dto.UserDTO;
import br.com.mydrafts.ApiMyDrafts.dto.UserFormDTO;
import br.com.mydrafts.ApiMyDrafts.exceptions.BusinessException;
import br.com.mydrafts.ApiMyDrafts.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO saveUser(UserFormDTO body) {
        if (!this.repository.existsByEmail(body.getEmail())) {
            User user = this.repository.save(modelMapper.map(body, User.class));
            return modelMapper.map(user, UserDTO.class);
        } else {
            throw new BusinessException(409, "CONFLICT", "Email is conflict");
        }
    }

    @Override
    public UserDTO searchUser(String id) {
        User user = this.repository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "BAD REQUEST", "User not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(String id, UserFormDTO body) {
        User user = this.repository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "BAD REQUEST", "User not found"));
        user.setName(body.getName());
        user.setGender(body.getGender());
        if (user.getEmail().equals(body.getEmail())) {
            user.setEmail(body.getEmail());
        } else {
            if (!this.repository.existsByEmail(body.getEmail())) {
                user.setEmail(body.getEmail());
            } else {
                throw new BusinessException(409, "CONFLICT", "Email is conflict");
            }
        }
        return modelMapper.map(this.repository.save(user), UserDTO.class);
    }

    @Override
    public void deleteUser(String id) {
        User user = this.repository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "BAD REQUEST", "User not found"));
        this.repository.delete(user);
    }
}
