package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.dto.UserDTO;
import br.com.mydrafts.ApiMyDrafts.dto.UserFormDTO;

public interface UserService {

    UserDTO saveUser(UserFormDTO body);

    UserDTO searchUser(String id);

    UserDTO updateUser(String id, UserFormDTO body);

    void deleteUser(String id);

}
