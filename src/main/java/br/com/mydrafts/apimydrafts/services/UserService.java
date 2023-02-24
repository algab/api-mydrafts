package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.dto.DraftDTO;
import br.com.mydrafts.apimydrafts.dto.FavoriteDTO;
import br.com.mydrafts.apimydrafts.dto.UserDTO;
import br.com.mydrafts.apimydrafts.dto.UserFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDTO save(UserFormDTO body);

    UserDTO search(String id);

    Page<DraftDTO> getDrafts(Pageable page, String id);

    Page<FavoriteDTO> getFavorites(Pageable page, String id);

    UserDTO update(String id, UserFormDTO body);

    void delete(String id);

}
