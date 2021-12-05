package br.com.mydrafts.apimydrafts.clients;

import br.com.mydrafts.apimydrafts.dto.tmdb.CreditsDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvDTO;
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

    public ResponseDTO trendingMovie() {
        return this.client.trendingMovie(this.apiKey, this.language);
    }

    public ResponseDTO trendingTV() {
        return this.client.trendingTv(this.apiKey, this.language);
    }

    public ResponseDTO searchMovie(String name) {
        return this.client.searchMovie(this.apiKey, this.language, name);
    }

    public ResponseDTO searchTV(String name) {
        return this.client.searchTv(this.apiKey, this.language, name);
    }

    public MovieDTO getMovie(Integer tmdbID) {
        return this.client.movie(tmdbID, this.apiKey, this.language);
    }

    public CreditsDTO getMovieCredits(Integer tmdbID) {
        CreditsDTO credits = this.client.movieCredits(tmdbID, this.apiKey, this.language);
        credits.setCrew(credits.getCrew().stream()
                .filter(crew -> crew.getJob().equals("Director") || crew.getJob().equals("Writer") || crew.getJob().equals("Executive Producer"))
                .collect(Collectors.toList()));
        return credits;
    }

    public TvDTO getTV(Integer tmdbID) {
        return this.client.tv(tmdbID, this.apiKey, this.language);
    }

}
