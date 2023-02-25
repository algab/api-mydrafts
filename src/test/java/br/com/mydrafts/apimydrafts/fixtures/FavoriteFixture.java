package br.com.mydrafts.apimydrafts.fixtures;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.FavoriteDocument;
import br.com.mydrafts.apimydrafts.dto.FavoriteDTO;
import br.com.mydrafts.apimydrafts.dto.FavoriteFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

public final class FavoriteFixture {

    public static FavoriteFormDTO getFavoriteForm() {
        return FavoriteFormDTO.builder()
            .media(Media.MOVIE)
            .tmdbID(550988)
            .userID("61586ad5362766670067edd5")
            .build();
    }

    public static FavoriteDocument getFavorite() {
        return FavoriteDocument.builder()
            .id("1000")
            .user(UserFixture.getUser())
            .production(ProductionFixture.getProductionMovie())
            .build();
    }

    public static FavoriteDTO getFavoriteDTO() {
        return FavoriteDTO.builder()
            .id("1")
            .production(ProductionFixture.getProductionMovieDTO())
            .build();
    }

    public static Page<FavoriteDocument> getPageFavoriteDocument() {
        return new PageImpl<>(
            Collections.singletonList(FavoriteFixture.getFavorite()),
            PageRequest.of(0, 10),
            1
        );
    }

    public static Page<FavoriteDTO> getPageFavoriteDTO() {
        return new PageImpl<>(
            Collections.singletonList(FavoriteFixture.getFavoriteDTO()),
            PageRequest.of(0, 10),
            1
        );
    }

}
