package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.dto.DraftDTO;
import br.com.mydrafts.ApiMyDrafts.dto.FavoriteDTO;
import br.com.mydrafts.ApiMyDrafts.dto.UserDTO;
import br.com.mydrafts.ApiMyDrafts.dto.UserFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDTO saveUser(UserFormDTO body);

    UserDTO searchUser(String id);

    Page<DraftDTO> getDrafts(Pageable page, String id);

    Page<FavoriteDTO> getFavorites(Pageable page, String id);

    UserDTO updateUser(String id, UserFormDTO body);

    void deleteUser(String id);

}
