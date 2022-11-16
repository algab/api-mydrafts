package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.fixtures.MediaFixture;
import br.com.mydrafts.apimydrafts.fixtures.ProductionFixture;
import br.com.mydrafts.apimydrafts.clients.TMDBProxy;
import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.converters.TMDBTvToResponse;
import br.com.mydrafts.apimydrafts.documents.Production;
import br.com.mydrafts.apimydrafts.repository.ProductionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Production Service")
class ProductionServiceTest {

    private ProductionService service;

    @Mock
    private ProductionRepository productionRepository;

    @Mock
    private TMDBProxy proxy;

    @BeforeEach
    void setup() {
        ModelMapper mapper = new ModelMapper();
        mapper.addConverter(new TMDBTvToResponse());
        service = new ProductionServiceImpl(productionRepository, proxy, mapper);
    }

    @Test
    @DisplayName("Service mount data production movie")
    void mountProductionMovieShouldReturnSuccessful() {
        when(proxy.getMovie(anyInt())).thenReturn(MediaFixture.movie());
        when(proxy.getMovieCredits(anyInt())).thenReturn(MediaFixture.credits());
        when(productionRepository.save(any())).thenReturn(getProductionMovie());

        Production production = service.mountProduction(10, Media.MOVIE, null);

        assertThat(getProductionMovie().getData().getId()).isEqualTo(production.getData().getId());
    }

    @Test
    @DisplayName("Service mount data production tv with season")
    void mountProductionTVSeasonShouldReturnSuccessful() {
        when(proxy.getTV(anyInt())).thenReturn(MediaFixture.tv());
        when(productionRepository.save(any())).thenReturn(getProductionTV());

        Production production = service.mountProduction(10, Media.TV, 1);

        assertThat(getProductionTV().getSeason()).isEqualTo(production.getSeason());
    }

    @Test
    @DisplayName("Service mount data production tv without season")
    void mountProductionTVShouldReturnSuccessful() {
        when(proxy.getTV(anyInt())).thenReturn(MediaFixture.tv());
        when(productionRepository.save(any())).thenReturn(getProductionTV());

        Production production = service.mountProduction(10, Media.TV, null);

        assertThat(getProductionTV().getData().getId()).isEqualTo(production.getData().getId());
    }

    @Test
    @DisplayName("Service search by tmdb id")
    void searchByTmdbIDShouldReturnSuccessful() {
        when(productionRepository.findByTmdbID(anyInt())).thenReturn(Optional.of(ProductionFixture.getProduction(Media.MOVIE)));

        Production production = service.searchByTmdbID(10);

        assertThat(ProductionFixture.getProduction(Media.MOVIE).getMedia()).isEqualTo(production.getMedia());
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
        when(productionRepository.findByTmdbIDAndSeason(anyInt(), anyInt())).thenReturn(Optional.of(ProductionFixture.getProduction(Media.TV)));

        Production production = service.searchByTmdbIdAndSeason(10, 1);

        assertThat(ProductionFixture.getProduction(Media.TV).getMedia()).isEqualTo(production.getMedia());
    }

    @Test
    @DisplayName("Service search by tmdb id and tv season not found")
    void searchByTmdbIDAndSeasonShouldReturnNotFound() {
        when(productionRepository.findByTmdbIDAndSeason(anyInt(), anyInt())).thenReturn(Optional.empty());

        Production production = service.searchByTmdbIdAndSeason(10, 1);

        assertThat(production).isNull();
    }

    private Production getProductionMovie() {
        Production production = ProductionFixture.getProduction(Media.MOVIE);
        production.setData(MediaFixture.getMovie());
        return production;
    }

    private Production getProductionTV() {
        Production production = ProductionFixture.getProduction(Media.TV);
        production.setData(MediaFixture.getTV());
        return production;
    }

}
