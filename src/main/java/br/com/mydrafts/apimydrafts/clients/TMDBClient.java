package br.com.mydrafts.apimydrafts.clients;

import br.com.mydrafts.apimydrafts.dto.tmdb.CreditsDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="TMDB", url="${tmdb.api-url}")
public interface TMDBClient {

    @GetMapping(value="/trending/movie/day", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    ResponseDTO trendingMovie(@RequestParam("api_key") String apiKey, @RequestParam("language") String language);

    @GetMapping(value="/trending/tv/day", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    ResponseDTO trendingTv(@RequestParam("api_key") String apiKey, @RequestParam("language") String language);

    @GetMapping(value="/search/movie", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    ResponseDTO searchMovie(
        @RequestParam("api_key") String apiKey,
        @RequestParam("language") String language,
        @RequestParam("query") String query
    );

    @GetMapping(value="/search/tv", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    ResponseDTO searchTv(
        @RequestParam("api_key") String apiKey,
        @RequestParam("language") String language,
        @RequestParam("query") String query
    );

    @GetMapping(value="/movie/{id}", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    MovieDTO movie(@PathVariable("id") Integer id, @RequestParam("api_key") String apiKey, @RequestParam("language") String language);

    @GetMapping(value="/movie/{id}/credits", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    CreditsDTO movieCredits(@PathVariable("id") Integer id, @RequestParam("api_key") String apiKey, @RequestParam("language") String language);

    @GetMapping(value="/tv/{id}", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    TvDTO tv(@PathVariable("id") Integer id, @RequestParam("api_key") String apiKey, @RequestParam("language") String language);

}
