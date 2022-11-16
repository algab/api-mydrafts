package br.com.mydrafts.apimydrafts.fixtures;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Favorite;
import br.com.mydrafts.apimydrafts.dto.FavoriteDTO;
import br.com.mydrafts.apimydrafts.dto.FavoriteFormDTO;

public final class FavoriteFixture {

    public static FavoriteFormDTO favoriteForm() {
        return FavoriteFormDTO.builder()
            .media(Media.MOVIE)
            .tmdbID(550988)
            .userID("61586ad5362766670067edd5")
            .build();
    }

    public static Favorite getFavorite(Media media) {
        return Favorite.builder()
            .id("1000")
            .user(UserFixture.getUser())
            .production(ProductionFixture.getProduction(media))
            .build();
    }

    public static FavoriteDTO getFavoriteDTO() {
        return FavoriteDTO.builder()
            .id("1")
            .production(ProductionFixture.getProductionDTO())
            .build();
    }

}
