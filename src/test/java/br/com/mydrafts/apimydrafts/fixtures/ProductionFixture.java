package br.com.mydrafts.apimydrafts.fixtures;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.ProductionDocument;
import br.com.mydrafts.apimydrafts.dto.ProductionDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.SeasonDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvResponseDTO;

import java.util.Collections;

public final class ProductionFixture {

    public static ProductionDocument getProductionMovie() {
        return ProductionDocument.builder()
            .id("6158fb48b7179927e035ae7b")
            .tmdbID(550988)
            .media(Media.MOVIE)
            .data(movie())
            .build();
    }

    public static ProductionDocument getProductionTV() {
        return ProductionDocument.builder()
            .id("6158fb48b7179927e035ae7b")
            .tmdbID(550989)
            .media(Media.TV)
            .data(tv())
            .build();
    }

    public static ProductionDTO getProductionMovieDTO() {
        return ProductionDTO.builder()
            .id("6158fb48b7179927e035ae7b")
            .tmdbID(550988)
            .media(Media.MOVIE)
            .build();
    }

    private static MovieResponseDTO movie() {
        return MovieResponseDTO.builder()
            .id(MediaFixture.getMovie().getId())
            .title(MediaFixture.getMovie().getTitle())
            .crew(MediaFixture.getMovie().getCrew())
            .build();
    }

    private static TvResponseDTO tv() {
        return TvResponseDTO.builder()
            .seasons(Collections.singletonList(SeasonDTO.builder().number(1).build()))
            .build();
    }

}
