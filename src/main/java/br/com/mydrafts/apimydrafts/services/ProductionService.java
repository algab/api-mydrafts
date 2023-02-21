package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Production;

import java.util.Optional;

public interface ProductionService {

    Production mountProduction(Integer tmdbID, Media media);

    Optional<Production> searchProduction(Integer tmdbID, Media media);

}
