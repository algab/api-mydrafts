package br.com.mydrafts.apimydrafts.repository;

import br.com.mydrafts.apimydrafts.documents.Favorite;
import br.com.mydrafts.apimydrafts.documents.Production;
import br.com.mydrafts.apimydrafts.documents.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FavoriteRepository extends MongoRepository<Favorite, String> {

    Boolean existsByUserAndProduction(User user, Production production);

    Page<Favorite> findByUser(User user, Pageable page);

}
