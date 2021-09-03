package br.com.mydrafts.ApiMyDrafts.clients;

import br.com.mydrafts.ApiMyDrafts.dto.TMDBMovieDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBResponseDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBTvDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="TMDB", url="${tmdb.api-url}")
public interface TMDBClient {
    @GetMapping(value="/trending/movie/day", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    TMDBResponseDTO trendingMovie(@RequestParam("api_key") String apiKey, @RequestParam("language") String language);

    @GetMapping(value="/trending/tv/day", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    TMDBResponseDTO trendingTv(@RequestParam("api_key") String apiKey, @RequestParam("language") String language);

    @GetMapping(value="/search/movie", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    TMDBResponseDTO searchMovie(
            @RequestParam("api_key") String apiKey,
            @RequestParam("language") String language,
            @RequestParam("query") String query
    );

    @GetMapping(value="/search/tv", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    TMDBResponseDTO searchTv(
            @RequestParam("api_key") String apiKey,
            @RequestParam("language") String language,
            @RequestParam("query") String query
    );

    @GetMapping(value="/movie/{id}", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    TMDBMovieDTO movie(@PathVariable("id") Integer id, @RequestParam("api_key") String apiKey, @RequestParam("language") String language);

    @GetMapping(value="/tv/{id}", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    TMDBTvDTO tv(@PathVariable("id") Integer id, @RequestParam("api_key") String apiKey, @RequestParam("language") String language);

    @GetMapping(value="/tv/{id}/season/{season_number}", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void tvSeason(
            @PathVariable("id") Integer id,
            @PathVariable("season_number") Integer number,
            @RequestParam("api_key") String apiKey,
            @RequestParam("language") String language
    );
}
