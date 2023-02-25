package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.dto.tmdb.MovieResponseDTO;
import br.com.mydrafts.apimydrafts.fixtures.MediaFixture;
import br.com.mydrafts.apimydrafts.fixtures.ProductionFixture;
import br.com.mydrafts.apimydrafts.clients.TMDBProxy;
import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.converters.TMDBTvToResponse;
import br.com.mydrafts.apimydrafts.documents.ProductionDocument;
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
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for ProductionService")
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
        ProductionDocument document = ProductionFixture.getProductionMovie();
        when(proxy.getMovie(10)).thenReturn(MediaFixture.movie());
        when(proxy.getMovieCredits(10)).thenReturn(MediaFixture.credits());
        when(productionRepository.save(any(ProductionDocument.class))).thenReturn(document);

        ProductionDocument production = service.mountProduction(10, Media.MOVIE);
        MovieResponseDTO movie = (MovieResponseDTO) production.getData();

        assertThat(movie.getId()).isEqualTo(document.getData().getId());
        assertThat(movie.getCrew()).isNotEmpty();
    }

    @Test
    @DisplayName("Service mount data production tv with season")
    void mountProductionTVSeasonShouldReturnSuccessful() {
        ProductionDocument document = ProductionFixture.getProductionTV();
        when(proxy.getTV(10)).thenReturn(MediaFixture.tv());
        when(productionRepository.save(any(ProductionDocument.class))).thenReturn(document);

        ProductionDocument production = service.mountProduction(10, Media.TV);

        assertThat(production.getData().getId()).isEqualTo(document.getData().getId());
    }

    @Test
    @DisplayName("Service mount data production tv without season")
    void mountProductionTVShouldReturnSuccessful() {
        ProductionDocument document = ProductionFixture.getProductionTV();
        when(proxy.getTV(10)).thenReturn(MediaFixture.tv());
        when(productionRepository.save(any(ProductionDocument.class))).thenReturn(document);

        ProductionDocument production = service.mountProduction(10, Media.TV);

        assertThat(production.getData().getId()).isEqualTo(document.getData().getId());
    }

    @Test
    @DisplayName("Service search by tmdb id")
    void searchByTmdbIDShouldReturnSuccessful() {
        ProductionDocument document = ProductionFixture.getProductionMovie();
        when(productionRepository.findByTmdbIDAndMedia(10, Media.MOVIE))
            .thenReturn(Optional.of(document));

        Optional<ProductionDocument> production = service.searchProduction(10, Media.MOVIE);

        assertThat(production).isPresent();
        assertThat(production.get().getMedia()).isEqualTo(document.getMedia());
    }

    @Test
    @DisplayName("Service search by tmdb id not found")
    void searchByTmdbIDShouldReturnNotFound() {
        when(productionRepository.findByTmdbIDAndMedia(10, Media.TV)).thenReturn(Optional.empty());

        Optional<ProductionDocument> production = service.searchProduction(10, Media.TV);

        assertThat(production).isEmpty();
    }

}
