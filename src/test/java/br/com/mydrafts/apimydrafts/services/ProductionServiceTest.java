package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.builder.MediaBuilder;
import br.com.mydrafts.apimydrafts.builder.ProductionBuilder;
import br.com.mydrafts.apimydrafts.clients.TMDBProxy;
import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Production;
import br.com.mydrafts.apimydrafts.repository.ProductionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Production Service")
class ProductionServiceTest {

    @Autowired
    private ProductionService service;

    @MockBean
    private ProductionRepository productionRepository;

    @MockBean
    private TMDBProxy proxy;

    @Test
    @DisplayName("Service mount data production movie")
    void mountProductionMovieShouldReturnSuccessful() {
        when(proxy.getMovie(anyInt())).thenReturn(MediaBuilder.movie());
        when(proxy.getMovieCredits(anyInt())).thenReturn(MediaBuilder.credits());
        when(productionRepository.save(any())).thenReturn(getProductionMovie());

        Production production = service.mountProduction(10, Media.MOVIE, null);

        assertThat(getProductionMovie().getData().getId()).isEqualTo(production.getData().getId());
    }

    @Test
    @DisplayName("Service mount data production tv with season")
    void mountProductionTVSeasonShouldReturnSuccessful() {
        when(proxy.getTV(anyInt())).thenReturn(MediaBuilder.tv());
        when(productionRepository.save(any())).thenReturn(getProductionTV());

        Production production = service.mountProduction(10, Media.TV, 1);

        assertThat(getProductionTV().getSeason()).isEqualTo(production.getSeason());
    }

    @Test
    @DisplayName("Service mount data production tv without season")
    void mountProductionTVShouldReturnSuccessful() {
        when(proxy.getTV(anyInt())).thenReturn(MediaBuilder.tv());
        when(productionRepository.save(any())).thenReturn(getProductionTV());

        Production production = service.mountProduction(10, Media.TV, null);

        assertThat(getProductionTV().getData().getId()).isEqualTo(production.getData().getId());
    }

    @Test
    @DisplayName("Service search by tmdb id")
    void searchByTmdbIDShouldReturnSuccessful() {
        when(productionRepository.findByTmdbID(anyInt())).thenReturn(Optional.of(ProductionBuilder.getProduction(Media.MOVIE)));

        Production production = service.searchByTmdbID(10);

        assertThat(ProductionBuilder.getProduction(Media.MOVIE).getMedia()).isEqualTo(production.getMedia());
    }

    @Test
    @DisplayName("Service search by tmdb id not found")
    void searchByTmdbIDShouldReturnNotFound() {
        when(productionRepository.findByTmdbID(anyInt())).thenReturn(Optional.empty());

        Production production = service.searchByTmdbID(10);

        assertThat(production).isNull();
    }

    @Test
    @DisplayName("Service search by tmdb id and tv season")
    void searchByTmdbIDAndSeasonShouldReturnSuccessful() {
        when(productionRepository.findByTmdbIDAndSeason(anyInt(), anyInt())).thenReturn(Optional.of(ProductionBuilder.getProduction(Media.TV)));

        Production production = service.searchByTmdbIdAndSeason(10, 1);

        assertThat(ProductionBuilder.getProduction(Media.TV).getMedia()).isEqualTo(production.getMedia());
    }

    @Test
    @DisplayName("Service search by tmdb id and tv season not found")
    void searchByTmdbIDAndSeasonShouldReturnNotFound() {
        when(productionRepository.findByTmdbIDAndSeason(anyInt(), anyInt())).thenReturn(Optional.empty());

        Production production = service.searchByTmdbIdAndSeason(10, 1);

        assertThat(production).isNull();
    }

    private Production getProductionMovie() {
        Production production = ProductionBuilder.getProduction(Media.MOVIE);
        production.setData(MediaBuilder.getMovie());
        return production;
    }

    private Production getProductionTV() {
        Production production = ProductionBuilder.getProduction(Media.TV);
        production.setData(MediaBuilder.getTV());
        return production;
    }

}
