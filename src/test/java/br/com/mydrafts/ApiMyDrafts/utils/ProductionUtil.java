package br.com.mydrafts.ApiMyDrafts.utils;

import br.com.mydrafts.ApiMyDrafts.constants.Media;
import br.com.mydrafts.ApiMyDrafts.documents.Production;
import br.com.mydrafts.ApiMyDrafts.dto.ProductionDTO;

public final class ProductionUtil {

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
                .media(Media.movie)
                .build();
    }

}
