package br.com.mydrafts.apimydrafts.fixtures;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Draft;
import br.com.mydrafts.apimydrafts.documents.Production;
import br.com.mydrafts.apimydrafts.dto.DraftDTO;
import br.com.mydrafts.apimydrafts.dto.DraftFormDTO;
import br.com.mydrafts.apimydrafts.dto.DraftUpdateFormDTO;
import br.com.mydrafts.apimydrafts.dto.ProductionDTO;

public final class DraftFixture {

    public static DraftFormDTO getDraftForm(Integer tmdbId, Media media, Integer season) {
        return DraftFormDTO.builder()
            .description("Very Good")
            .media(media)
            .rating(5)
            .tmdbID(tmdbId)
            .season(season)
            .userID("61586ad5362766670067edd5")
            .build();
    }

    public static DraftUpdateFormDTO getDraftUpdateForm(Integer season) {
        return DraftUpdateFormDTO.builder()
            .description("Very Good")
            .rating(5)
            .userID("61586ad5362766670067edd5")
            .season(season)
            .build();
    }

    public static Draft getDraft(Production production) {
        return Draft.builder()
            .id("61586ad5362766670067eda8")
            .description("Very Good")
            .rating(5)
            .user(UserFixture.getUser())
            .production(production)
            .build();
    }

    public static DraftDTO getDraftDTO(ProductionDTO production) {
        return DraftDTO.builder()
            .id("61586ad5362766670067eda8")
            .description("Very Good")
            .rating(5)
            .production(production)
            .build();
    }

}
