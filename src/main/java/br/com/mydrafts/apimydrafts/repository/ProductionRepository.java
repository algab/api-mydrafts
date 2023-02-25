package br.com.mydrafts.apimydrafts.repository;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.ProductionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductionRepository extends MongoRepository<ProductionDocument, String> {

    Optional<ProductionDocument> findByTmdbIDAndMedia(Integer tmdbID, Media media);

}
