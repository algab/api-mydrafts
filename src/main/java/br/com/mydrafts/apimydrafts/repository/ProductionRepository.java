package br.com.mydrafts.apimydrafts.repository;

import br.com.mydrafts.apimydrafts.documents.Production;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductionRepository extends MongoRepository<Production, String> {

    Optional<Production> findByTmdbID(Integer tmdbID);

    Optional<Production> findByTmdbIDAndSeason(Integer tmdbId, Integer season);

}
