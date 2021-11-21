package br.com.mydrafts.apimydrafts.clients;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Production;
import br.com.mydrafts.apimydrafts.dto.tmdb.CreditsDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.SeasonDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvSeasonDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TMDBProxy {

    @Value("${tmdb.api-key}")
    private String apiKey;

    @Value("${tmdb.language}")
    private String language;

    @Autowired
    private TMDBClient client;

    @Autowired
    private ModelMapper mapper;

    public ResponseDTO trendingMovie() {
        try {
            return this.client.trendingMovie(this.apiKey, this.language);
        } catch (FeignException exception) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.contentUTF8());
        }
    }

    public ResponseDTO trendingTV() {
        try {
            return this.client.trendingTv(this.apiKey, this.language);
        } catch (FeignException exception) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.contentUTF8());
        }
    }

    public ResponseDTO searchMovie(String name) {
        try {
            return this.client.searchMovie(this.apiKey, this.language, name);
        } catch (FeignException exception) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.contentUTF8());
        }
    }

    public ResponseDTO searchTV(String name) {
        try {
            return this.client.searchTv(this.apiKey, this.language, name);
        } catch (FeignException exception) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.contentUTF8());
        }
    }

    public MovieDTO getMovie(Integer tmdbID) {
        try {
            return this.client.movie(tmdbID, this.apiKey, this.language);
        } catch (FeignException exception) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.contentUTF8());
        }
    }

    public CreditsDTO getMovieCredits(Integer tmdbID) {
        try {
            CreditsDTO credits = this.client.movieCredits(tmdbID, this.apiKey, this.language);
            credits.setCrew(credits.getCrew().stream()
                    .filter(crew -> crew.getJob().equals("Director") || crew.getJob().equals("Writer") || crew.getJob().equals("Executive Producer"))
                    .collect(Collectors.toList()));
            return credits;
        } catch (FeignException exception) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.contentUTF8());
        }
    }

    public TvDTO getTV(Integer tmdbID) {
        try {
            return this.client.tv(tmdbID, this.apiKey, this.language);
        } catch (FeignException exception) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.contentUTF8());
        }
    }

    public Production findProduction(Media media, Integer tmdbID, Integer numberSeason) {
        Production production = Production.builder().media(media).tmdbID(tmdbID).build();
        if (media.equals(Media.MOVIE)) {
            MovieDTO movie = getMovie(tmdbID);
            CreditsDTO credits = getMovieCredits(tmdbID);
            MovieResponseDTO response = mapper.map(movie, MovieResponseDTO.class);
            response.setCrew(credits.getCrew());
            production.setMovie(response);
        } else {
            if (numberSeason != null) {
                TvResponseDTO tv = mapper.map(getTV(tmdbID), TvResponseDTO.class);
                TvSeasonDTO tvSeason = mapper.map(tv, TvSeasonDTO.class);
                SeasonDTO season = tv.getSeasons().stream().filter(data -> data.getNumber() == numberSeason).collect(Collectors.toList()).get(0);
                tvSeason.setId(season.getId());
                tvSeason.setSeason(season.getNumber());
                tvSeason.setDateRelease(season.getDate());
                production.setTv(tvSeason);
            }
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), "Season is required");
        }
        return production;
    }

}
