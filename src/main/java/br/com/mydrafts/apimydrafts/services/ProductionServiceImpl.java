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

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ProductionServiceImpl implements ProductionService {

    private ProductionRepository repository;

    private TMDBProxy tmdbProxy;

    private ModelMapper mapper;

    @Override
    public Production mountProduction(Integer tmdbID, Media media, Integer season) {
        log.info("ProductionServiceImpl.mountProduction - Input: tmdbID {}, season {}, media {}", tmdbID, season, media);

        Production production = Production.builder().media(media).tmdbID(tmdbID).build();
        if (media.equals(Media.MOVIE)) {
            dataMovie(tmdbID, production);
        } else {
            dataTV(tmdbID, season, production);
        }
        Production productionResponse = this.repository.save(production);

        log.info("ProductionServiceImpl.mountProduction - Input: tmdbID {}, season {}, media {} - Output: {}", tmdbID, season, media, productionResponse);
        return productionResponse;
    }

    @Override
    public Production searchByTmdbID(Integer tmdbID) {
        Optional<Production> production = this.repository.findByTmdbID(tmdbID);
        return production.orElse(null);
    }

    @Override
    public Production searchByTmdbIdAndSeason(Integer tmdbID, Integer season) {
        Optional<Production> production = this.repository.findByTmdbIDAndSeason(tmdbID, season);
        return production.orElse(null);
    }

    private void dataMovie(Integer tmdbID, Production production) {
        MovieDTO movie = tmdbProxy.getMovie(tmdbID);
        CreditsDTO credits = tmdbProxy.getMovieCredits(tmdbID);
        MovieResponseDTO response = mapper.map(movie, MovieResponseDTO.class);
        response.setCrew(credits.getCrew());
        production.setData(response);
    }

    private void dataTV(Integer tmdbID, Integer season, Production production) {
        TvDTO tv = tmdbProxy.getTV(tmdbID);
        TvResponseDTO tvResponse = mapper.map(tv, TvResponseDTO.class);
        if (season != null) {
            SeasonResponseDTO tvSeason = mapper.map(tv, SeasonResponseDTO.class);
            SeasonDTO findSeason = tv.getSeasons().stream().filter(data -> data.getNumber().equals(season)).collect(Collectors.toList()).get(0);
            tvSeason.setSeason(findSeason.getNumber());
            tvSeason.setDateRelease(findSeason.getDate());
            production.setData(tvSeason);
            production.setSeason(season);
        } else {
            production.setData(tvResponse);
        }
    }

}
