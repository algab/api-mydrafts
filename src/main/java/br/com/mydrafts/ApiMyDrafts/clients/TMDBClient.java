package br.com.mydrafts.ApiMyDrafts.clients;

import br.com.mydrafts.ApiMyDrafts.dto.TMDBResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="TMDB", url="${tmdb.api-url}")
public interface TMDBClient {
    @GetMapping(value="/trending/movie/day", produces= MediaType.APPLICATION_JSON_VALUE)
    TMDBResponseDTO trendingMovie(@RequestParam("api_key") String apiKey, @RequestParam("language") String language);

    @GetMapping(value="/trending/tv/day", produces= MediaType.APPLICATION_JSON_VALUE)
    TMDBResponseDTO trendingTv(@RequestParam("api_key") String apiKey, @RequestParam("language") String language);

    @GetMapping(value="/search/movie", produces= MediaType.APPLICATION_JSON_VALUE)
    TMDBResponseDTO searchMovie(
            @RequestParam("api_key") String apiKey,
            @RequestParam("language") String language,
            @RequestParam("query") String query
    );

    @GetMapping(value="/search/tv", produces= MediaType.APPLICATION_JSON_VALUE)
    TMDBResponseDTO searchTv(
            @RequestParam("api_key") String apiKey,
            @RequestParam("language") String language,
            @RequestParam("query") String query
    );

    @GetMapping(value="/movie/${id}", produces= MediaType.APPLICATION_JSON_VALUE)
    void movie(@PathVariable("id") Integer id, @RequestParam("api_key") String apiKey, @RequestParam("language") String language);

    @GetMapping(value="/tv/${id}", produces= MediaType.APPLICATION_JSON_VALUE)
    void tv(@PathVariable("id") Integer id, @RequestParam("api_key") String apiKey, @RequestParam("language") String language);

    @GetMapping(value="/tv/${id}/season/${season_number}", produces= MediaType.APPLICATION_JSON_VALUE)
    void tvSeason(@PathVariable("id") Integer id,
                  @PathVariable("season_number") Integer number,
                  @RequestParam("api_key") String apiKey,
                  @RequestParam("language") String language);
}
