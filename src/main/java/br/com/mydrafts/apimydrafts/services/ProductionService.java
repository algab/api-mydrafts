package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Production;

public interface ProductionService {

    Production mountProduction(Integer tmdbID, Integer season, Media media);

}
