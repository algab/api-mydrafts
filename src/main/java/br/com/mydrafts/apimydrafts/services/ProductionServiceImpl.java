package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.clients.TMDBProxy;
import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.ProductionDocument;
import br.com.mydrafts.apimydrafts.dto.tmdb.CreditsDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvResponseDTO;
import br.com.mydrafts.apimydrafts.repository.ProductionRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ProductionServiceImpl implements ProductionService {

    private ProductionRepository repository;

    private TMDBProxy tmdbProxy;

    private ModelMapper mapper;

    @Override
    public ProductionDocument mountProduction(Integer tmdbID, Media media) {
        ProductionDocument production = ProductionDocument.builder().media(media).tmdbID(tmdbID).build();
        if (media.equals(Media.MOVIE)) {
            dataMovie(tmdbID, production);
        } else {
            dataTV(tmdbID, production);
        }
        return this.repository.save(production);
    }

    @Override
    public Optional<ProductionDocument> searchProduction(Integer tmdbID, Media media) {
        return this.repository.findByTmdbIDAndMedia(tmdbID, media);
    }

    private void dataMovie(Integer tmdbID, ProductionDocument production) {
        MovieDTO movie = tmdbProxy.getMovie(tmdbID);
        CreditsDTO credits = tmdbProxy.getMovieCredits(tmdbID);
        MovieResponseDTO movieResponse = mapper.map(movie, MovieResponseDTO.class);
        movieResponse.setCrew(credits.getCrew());
        production.setData(movieResponse);
    }

    private void dataTV(Integer tmdbID, ProductionDocument production) {
        TvDTO tv = tmdbProxy.getTV(tmdbID);
        production.setData(mapper.map(tv, TvResponseDTO.class));
    }

}
