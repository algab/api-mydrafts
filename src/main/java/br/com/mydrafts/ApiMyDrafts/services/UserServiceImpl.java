package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.documents.User;
import br.com.mydrafts.ApiMyDrafts.dto.UserDTO;
import br.com.mydrafts.ApiMyDrafts.dto.UserFormDTO;
import br.com.mydrafts.ApiMyDrafts.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO saveUser(UserFormDTO body) {
        User user = this.repository.save(modelMapper.map(body, User.class));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO searchUser(String id) {
        return modelMapper.map(this.repository.findById(id).get(), UserDTO.class);
    }

    @Override
    public UserDTO updateUser(String id, UserFormDTO body) {
        Optional<User> user = this.repository.findById(id);
        user.get().setName(body.getName());
        user.get().setEmail(body.getEmail());
        user.get().setGender(body.getGender());
        return modelMapper.map(this.repository.save(user.get()), UserDTO.class);
    }

    @Override
    public void deleteUser(String id) {
        Optional<User> user = this.repository.findById(id);
        this.repository.delete(user.get());
    }
}
