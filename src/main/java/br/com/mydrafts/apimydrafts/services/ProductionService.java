package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.ProductionDocument;

import java.util.Optional;

public interface ProductionService {

    ProductionDocument mountProduction(Integer tmdbID, Media media);

    Optional<ProductionDocument> searchProduction(Integer tmdbID, Media media);

}
