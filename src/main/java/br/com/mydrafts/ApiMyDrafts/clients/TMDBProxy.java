package br.com.mydrafts.ApiMyDrafts.clients;

import br.com.mydrafts.ApiMyDrafts.constants.Media;
import br.com.mydrafts.ApiMyDrafts.documents.Production;
import br.com.mydrafts.ApiMyDrafts.dto.*;
import br.com.mydrafts.ApiMyDrafts.exceptions.BusinessException;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private static final Integer STATUS_SERVER_ERROR = 500;
    private static final String MESSAGE_SERVER_ERROR = "INTERNAL SERVER ERROR";

    public TMDBResponseDTO trendingMovie() {
        try {
            return this.client.trendingMovie(this.apiKey, this.language);
        } catch (FeignException exception) {
            throw new BusinessException(STATUS_SERVER_ERROR, MESSAGE_SERVER_ERROR, exception.contentUTF8());
        }
    }

    public TMDBResponseDTO trendingTV() {
        try {
            return this.client.trendingTv(this.apiKey, this.language);
        } catch (FeignException exception) {
            throw new BusinessException(STATUS_SERVER_ERROR, MESSAGE_SERVER_ERROR, exception.contentUTF8());
        }
    }

    public TMDBResponseDTO searchMovie(String name) {
        try {
            return this.client.searchMovie(this.apiKey, this.language, name);
        } catch (FeignException exception) {
            throw new BusinessException(STATUS_SERVER_ERROR, MESSAGE_SERVER_ERROR, exception.contentUTF8());
        }
    }

    public TMDBResponseDTO searchTV(String name) {
        try {
            return this.client.searchTv(this.apiKey, this.language, name);
        } catch (FeignException exception) {
            throw new BusinessException(STATUS_SERVER_ERROR, MESSAGE_SERVER_ERROR, exception.contentUTF8());
        }
    }

    public TMDBMovieDTO getMovie(Integer tmdbID) {
        try {
            return this.client.movie(tmdbID, this.apiKey, this.language);
        } catch (FeignException exception) {
            throw new BusinessException(STATUS_SERVER_ERROR, MESSAGE_SERVER_ERROR, exception.contentUTF8());
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
            throw new BusinessException(STATUS_SERVER_ERROR, MESSAGE_SERVER_ERROR, exception.contentUTF8());
        }
    }

    public TMDBTvDTO getTV(Integer tmdbID) {
        try {
            return this.client.tv(tmdbID, this.apiKey, this.language);
        } catch (FeignException exception) {
            throw new BusinessException(STATUS_SERVER_ERROR, MESSAGE_SERVER_ERROR, exception.contentUTF8());
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
