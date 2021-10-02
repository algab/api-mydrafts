package br.com.mydrafts.ApiMyDrafts.repository;

import br.com.mydrafts.ApiMyDrafts.documents.Production;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductionRepository extends MongoRepository<Production, String> {

    Optional<Production> findByTmdbID(Integer tmdbID);

}
