package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.clients.TMDBProxy;
import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Production;
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
    public Production mountProduction(Integer tmdbID, Media media) {
        log.info("ProductionServiceImpl.mountProduction - Input: tmdbID {}, media {}", tmdbID, media);

        Production production = Production.builder().media(media).tmdbID(tmdbID).build();
        if (media.equals(Media.MOVIE)) {
            dataMovie(tmdbID, production);
        } else {
            dataTV(tmdbID, production);
        }
        Production productionResponse = this.repository.save(production);

        log.info("ProductionServiceImpl.mountProduction - Input: tmdbID {}, media {} - Output: {}", tmdbID, media, productionResponse);
        return productionResponse;
    }

    @Override
    public Optional<Production> searchProduction(Integer tmdbID, Media media) {
        return this.repository.findByTmdbIDAndMedia(tmdbID, media);
    }

    private void dataMovie(Integer tmdbID, Production production) {
        MovieDTO movie = tmdbProxy.getMovie(tmdbID);
        CreditsDTO credits = tmdbProxy.getMovieCredits(tmdbID);
        MovieResponseDTO movieResponse = mapper.map(movie, MovieResponseDTO.class);
        movieResponse.setCrew(credits.getCrew());
        production.setData(movieResponse);
    }

    private void dataTV(Integer tmdbID, Production production) {
        TvDTO tv = tmdbProxy.getTV(tmdbID);
        production.setData(mapper.map(tv, TvResponseDTO.class));
    }

}
