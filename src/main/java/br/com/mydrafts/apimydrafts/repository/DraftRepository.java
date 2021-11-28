package br.com.mydrafts.apimydrafts.repository;

import br.com.mydrafts.apimydrafts.documents.Draft;
import br.com.mydrafts.apimydrafts.documents.Production;
import br.com.mydrafts.apimydrafts.documents.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DraftRepository extends MongoRepository<Draft, String> {

    boolean existsByUserAndProduction(User user, Production production);

    Page<Draft> findByUser(User user, Pageable page);

}
