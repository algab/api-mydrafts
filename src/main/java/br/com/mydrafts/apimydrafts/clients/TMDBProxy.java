package br.com.mydrafts.apimydrafts.clients;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Production;
import br.com.mydrafts.apimydrafts.dto.*;
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

    public TMDBResponseDTO trendingMovie() {
        try {
            return this.client.trendingMovie(this.apiKey, this.language);
        } catch (FeignException exception) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.contentUTF8());
        }
    }

    public TMDBResponseDTO trendingTV() {
        try {
            return this.client.trendingTv(this.apiKey, this.language);
        } catch (FeignException exception) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.contentUTF8());
        }
    }

    public TMDBResponseDTO searchMovie(String name) {
        try {
            return this.client.searchMovie(this.apiKey, this.language, name);
        } catch (FeignException exception) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.contentUTF8());
        }
    }

    public TMDBResponseDTO searchTV(String name) {
        try {
            return this.client.searchTv(this.apiKey, this.language, name);
        } catch (FeignException exception) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.contentUTF8());
        }
    }

    public TMDBMovieDTO getMovie(Integer tmdbID) {
        try {
            return this.client.movie(tmdbID, this.apiKey, this.language);
        } catch (FeignException exception) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.contentUTF8());
        }
    }

    public TMDBCreditsDTO getMovieCredits(Integer tmdbID) {
        try {
            TMDBCreditsDTO credits = this.client.movieCredits(tmdbID, this.apiKey, this.language);
            credits.setCrew(credits.getCrew().stream()
                    .filter(crew -> crew.getJob().equals("Director") || crew.getJob().equals("Writer") || crew.getJob().equals("Executive Producer"))
                    .collect(Collectors.toList()));
            return credits;
        } catch (FeignException exception) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.contentUTF8());
        }
    }

    public TMDBTvDTO getTV(Integer tmdbID) {
        try {
            return this.client.tv(tmdbID, this.apiKey, this.language);
        } catch (FeignException exception) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.contentUTF8());
        }
    }

    public Production findProduction(Media media, Integer tmdbID) {
        Production production = Production.builder().media(media).tmdbID(tmdbID).build();
        if (media.equals(Media.MOVIE)) {
            TMDBMovieDTO movie = getMovie(tmdbID);
            TMDBCreditsDTO credits = getMovieCredits(tmdbID);
            TMDBMovieResponseDTO response = mapper.map(movie, TMDBMovieResponseDTO.class);
            response.setCrew(credits.getCrew());
            production.setMovie(response);
        } else {
            TMDBTvResponseDTO tv = mapper.map(getTV(tmdbID), TMDBTvResponseDTO.class);
            production.setTv(tv);
        }
        return production;
    }

}
