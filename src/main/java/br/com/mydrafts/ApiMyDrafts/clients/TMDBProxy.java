package br.com.mydrafts.ApiMyDrafts.clients;

import br.com.mydrafts.ApiMyDrafts.dto.TMDBCreditsDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBMovieDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBResponseDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBTvDTO;
import br.com.mydrafts.ApiMyDrafts.exceptions.BusinessException;
import feign.FeignException;
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

    public TMDBResponseDTO trendingMovie() {
        try {
            return this.client.trendingMovie(this.apiKey, this.language);
        } catch (FeignException exception) {
            throw new BusinessException(exception.status(), HttpStatus.resolve(exception.status()).name(), exception.contentUTF8());
        }
    }

    public TMDBResponseDTO trendingTV() {
        try {
            return this.client.trendingTv(this.apiKey, this.language);
        } catch (FeignException exception) {
            throw new BusinessException(exception.status(), HttpStatus.resolve(exception.status()).name(), exception.contentUTF8());
        }
    }

    public TMDBResponseDTO searchMovie(String name) {
        try {
            return this.client.searchMovie(this.apiKey, this.language, name);
        } catch (FeignException exception) {
            throw new BusinessException(exception.status(), HttpStatus.resolve(exception.status()).name(), exception.contentUTF8());
        }
    }

    public TMDBResponseDTO searchTV(String name) {
        try {
            return this.client.searchTv(this.apiKey, this.language, name);
        } catch (FeignException exception) {
            throw new BusinessException(exception.status(), HttpStatus.resolve(exception.status()).name(), exception.contentUTF8());
        }
    }

    public TMDBMovieDTO getMovie(Integer tmdbID) {
        try {
            return this.client.movie(tmdbID, this.apiKey, this.language);
        } catch (FeignException exception) {
            throw new BusinessException(exception.status(), HttpStatus.resolve(exception.status()).name(), exception.contentUTF8());
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
            throw new BusinessException(exception.status(), HttpStatus.resolve(exception.status()).name(), exception.contentUTF8());
        }
    }

    public TMDBTvDTO getTV(Integer tmdbID) {
        try {
            return this.client.tv(tmdbID, this.apiKey, this.language);
        } catch (FeignException exception) {
            throw new BusinessException(exception.status(), HttpStatus.resolve(exception.status()).name(), exception.contentUTF8());
        }
    }

}
