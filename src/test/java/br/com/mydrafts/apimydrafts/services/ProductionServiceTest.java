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

        Production production = service.mountProduction(10, Media.MOVIE);

        assertThat(getProductionMovie().getData().getId()).isEqualTo(production.getData().getId());
    }

    @Test
    @DisplayName("Service mount data production tv with season")
    void mountProductionTVSeasonShouldReturnSuccessful() {
        when(proxy.getTV(anyInt())).thenReturn(MediaFixture.tv());
        when(productionRepository.save(any())).thenReturn(getProductionTV());

        Production production = service.mountProduction(10, Media.TV);

        assertThat(production.getData().getId()).isEqualTo(getProductionTV().getData().getId());
    }

    @Test
    @DisplayName("Service mount data production tv without season")
    void mountProductionTVShouldReturnSuccessful() {
        when(proxy.getTV(anyInt())).thenReturn(MediaFixture.tv());
        when(productionRepository.save(any())).thenReturn(getProductionTV());

        Production production = service.mountProduction(10, Media.TV);

        assertThat(getProductionTV().getData().getId()).isEqualTo(production.getData().getId());
    }

    @Test
    @DisplayName("Service search by tmdb id")
    void searchByTmdbIDShouldReturnSuccessful() {
        Production productionMovie = ProductionFixture.getProductionMovie();
        when(productionRepository.findByTmdbIDAndMedia(anyInt(), any()))
            .thenReturn(Optional.of(productionMovie));

        Optional<Production> production = service.searchProduction(10, Media.MOVIE);

        assertThat(production).isPresent();
        assertThat(production.get().getMedia()).isEqualTo(productionMovie.getMedia());
    }

    @Test
    @DisplayName("Service search by tmdb id not found")
    void searchByTmdbIDShouldReturnNotFound() {
        when(productionRepository.findByTmdbIDAndMedia(anyInt(), any())).thenReturn(Optional.empty());

        Optional<Production> production = service.searchProduction(10, Media.TV);

        assertThat(production).isEmpty();
    }

    private Production getProductionMovie() {
        Production production = ProductionFixture.getProductionMovie();
        production.setData(MediaFixture.getMovie());
        return production;
    }

    private Production getProductionTV() {
        Production production = ProductionFixture.getProductionTV();
        production.setData(MediaFixture.getTV());
        return production;
    }

}
