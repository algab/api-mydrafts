package br.com.mydrafts.ApiMyDrafts.utils;

import br.com.mydrafts.ApiMyDrafts.constants.Media;
import br.com.mydrafts.ApiMyDrafts.documents.Draft;
import br.com.mydrafts.ApiMyDrafts.dto.DraftDTO;
import br.com.mydrafts.ApiMyDrafts.dto.DraftFormDTO;

public final class DraftUtil {

    public static DraftFormDTO draftForm(Media media) {
        return DraftFormDTO.builder()
                .description("Very Good")
                .media(media)
                .rating(10D)
                .tmdbID(550988)
                .userID("61586ad5362766670067edd5")
                .build();
    }

    public static Draft getDraft(Media media) {
        return Draft.builder()
                .id("61586ad5362766670067eda8")
                .description("Very Good")
                .rating(10D)
                .user(UserUtil.getUser())
                .production(ProductionUtil.getProduction(media))
                .build();
    }

    public static DraftDTO getDraftDTO() {
        return DraftDTO.builder()
                .id("61586ad5362766670067eda8")
                .description("Very Good")
                .rating(10D)
                .user(UserUtil.getUserDTO())
                .production(ProductionUtil.getProductionDTO())
                .build();
    }

}
