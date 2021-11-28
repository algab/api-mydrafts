package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.clients.TMDBProxy;
import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Production;
import br.com.mydrafts.apimydrafts.dto.tmdb.CreditsDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.SeasonDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.SeasonResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvResponseDTO;
import br.com.mydrafts.apimydrafts.repository.ProductionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductionServiceImpl implements ProductionService {

    @Autowired
    private ProductionRepository repository;

    @Autowired
    private TMDBProxy tmdbProxy;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Production mountProduction(Integer tmdbID, Integer season, Media media) {
        log.info("ProductionServiceImpl.mountProduction - Input: tmdbID {}, season {}, media {}", tmdbID, season, media);

        Production production = Production.builder().media(media).tmdbID(tmdbID).build();
        if (media.equals(Media.MOVIE)) {
            dataMovie(tmdbID, production);
        } else {
            dataTV(tmdbID, season, production);
        }

        Production productionResponse = this.saveProduction(production);
        log.info("ProductionServiceImpl.mountProduction - Input: tmdbID {}, season {}, media {} - Output: {}", tmdbID, season, media, productionResponse);
        return productionResponse;
    }

    private Production saveProduction(Production production) {
        return this.repository.save(production);
    }

    private void dataMovie(Integer tmdbID, Production production) {
        MovieDTO movie = tmdbProxy.getMovie(tmdbID);
        CreditsDTO credits = tmdbProxy.getMovieCredits(tmdbID);
        MovieResponseDTO response = mapper.map(movie, MovieResponseDTO.class);
        response.setCrew(credits.getCrew());
        production.setProduction(response);
    }

    private void dataTV(Integer tmdbID, Integer season, Production production) {
        TvDTO tv = tmdbProxy.getTV(tmdbID);
        TvResponseDTO tvResponse = mapper.map(tv, TvResponseDTO.class);
        if (season != null) {
            SeasonResponseDTO tvSeason = mapper.map(tv, SeasonResponseDTO.class);
            SeasonDTO findSeason = tv.getSeasons().stream().filter(data -> data.getNumber().equals(season)).collect(Collectors.toList()).get(0);
            tvSeason.setSeason(findSeason.getNumber());
            tvSeason.setDateRelease(findSeason.getDate());
            production.setProduction(tvSeason);
        } else {
            production.setProduction(tvResponse);
        }
    }

}
