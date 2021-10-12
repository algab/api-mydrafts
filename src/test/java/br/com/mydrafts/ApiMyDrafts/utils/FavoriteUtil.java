package br.com.mydrafts.ApiMyDrafts.utils;

import br.com.mydrafts.ApiMyDrafts.constants.Media;
import br.com.mydrafts.ApiMyDrafts.documents.Favorite;
import br.com.mydrafts.ApiMyDrafts.dto.FavoriteDTO;
import br.com.mydrafts.ApiMyDrafts.dto.FavoriteFormDTO;

public final class FavoriteUtil {

    public static FavoriteFormDTO favoriteForm() {
        return FavoriteFormDTO.builder()
                .media(Media.movie)
                .tmdbID(550988)
                .userID("61586ad5362766670067edd5")
                .build();
    }

    public static Favorite getFavorite(Media media) {
        return Favorite.builder()
                .id("1000")
                .user(UserUtil.getUser())
                .production(ProductionUtil.getProduction(media))
                .build();
    }

    public static FavoriteDTO getFavoriteDTO() {
        return FavoriteDTO.builder()
                .id("1")
                .user(UserUtil.getUserDTO())
                .production(ProductionUtil.getProductionDTO())
                .build();
    }

}
