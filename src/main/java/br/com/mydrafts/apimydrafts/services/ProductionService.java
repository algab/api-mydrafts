package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Production;

public interface ProductionService {

    Production mountProduction(Integer tmdbID, Media media, Integer season);

    Production searchByTmdbID(Integer tmdbID);

    Production searchByTmdbIdAndSeason(Integer tmdbID, Integer season);

}
