package br.com.mydrafts.apimydrafts.repository;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Production;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductionRepository extends MongoRepository<Production, String> {

    Optional<Production> findByTmdbIDAndMedia(Integer tmdbID, Media media);

}
