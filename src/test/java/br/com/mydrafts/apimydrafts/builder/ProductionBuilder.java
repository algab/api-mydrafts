package br.com.mydrafts.apimydrafts.builder;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Production;
import br.com.mydrafts.apimydrafts.dto.ProductionDTO;

public final class ProductionBuilder {

    public static Production getProduction(Media media) {
        return Production.builder()
                .id("6158fb48b7179927e035ae7b")
                .tmdbID(550988)
                .media(media)
                .build();
    }

    public static ProductionDTO getProductionDTO() {
        return ProductionDTO.builder()
                .id("6158fb48b7179927e035ae7b")
                .tmdbID(550988)
                .media(Media.MOVIE)
                .build();
    }

}
