package br.com.mydrafts.ApiMyDrafts.repository;

import br.com.mydrafts.ApiMyDrafts.documents.Favorite;
import br.com.mydrafts.ApiMyDrafts.documents.Production;
import br.com.mydrafts.ApiMyDrafts.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FavoriteRepository extends MongoRepository<Favorite, String> {

    Boolean existsByUserAndProduction(UserDTO user, Production production);

    Page<Favorite> findByUser(UserDTO user, Pageable page);

}
