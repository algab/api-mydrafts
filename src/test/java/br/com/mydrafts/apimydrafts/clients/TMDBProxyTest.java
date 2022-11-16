package br.com.mydrafts.apimydrafts.clients;

import br.com.mydrafts.apimydrafts.dto.tmdb.CreditsDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvDTO;
import br.com.mydrafts.apimydrafts.builder.MediaBuilder;
import br.com.mydrafts.apimydrafts.builder.SearchBuilder;
import br.com.mydrafts.apimydrafts.builder.TrendingBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for TMDBProxy")
class TMDBProxyTest {

    private TMDBProxy tmdbProxy;

    @Mock
    private TMDBClient tmdbClient;

    private static final String NAME_MOVIE = "Shang-Chi";
    private static final String NAME_TV_SHOW = "What If";

    @BeforeEach
    void setup() {
        tmdbProxy = new TMDBProxy(tmdbClient);
    }

    @Test
    @DisplayName("Trending movie successful")
    void trendingMovieSuccessful() {
        when(tmdbClient.trendingMovie(any(), any())).thenReturn(TrendingBuilder.responseTrendingMovie());

        ResponseDTO trendingMovie = tmdbProxy.trendingMovie();

        assertThat(trendingMovie.getResults()).isNotEmpty();
    }

    @Test
    @DisplayName("Trending tv successful")
    void trendingTVSuccessful() {
        when(tmdbClient.trendingTv(any(), any())).thenReturn(TrendingBuilder.responseTrendingTV());

        ResponseDTO trendingTV = tmdbProxy.trendingTV();

        assertThat(trendingTV.getResults()).isNotEmpty();
    }

    @Test
    @DisplayName("Search movie successful")
    void searchMovieSuccessful() {
        when(tmdbClient.searchMovie(any(), any(), anyString())).thenReturn(SearchBuilder.responseSearchMovie());

        ResponseDTO searchMovie = tmdbProxy.searchMovie(NAME_MOVIE);

        assertThat(searchMovie.getResults()).hasSize(SearchBuilder.responseSearchMovie().getResults().size());
    }

    @Test
    @DisplayName("Search tv successful")
    void searchTVSuccessful() {
        when(tmdbClient.searchTv(any(), any(), anyString())).thenReturn(SearchBuilder.responseSearchTV());

        ResponseDTO searchTV = tmdbProxy.searchTV(NAME_TV_SHOW);

        assertThat(searchTV.getResults()).hasSize(SearchBuilder.responseSearchTV().getResults().size());
    }

    @Test
    @DisplayName("Get movie successful")
    void getMovieSuccessful() {
        when(tmdbClient.movie(anyInt(), any(), any())).thenReturn(MediaBuilder.movie());

        MovieDTO movie = tmdbProxy.getMovie(1);

        assertThat(movie.getTitle()).isEqualTo(MediaBuilder.getMovie().getTitle());
        assertThat(movie.getDateRelease()).isEqualTo(MediaBuilder.getMovie().getDateRelease());
    }

    @Test
    @DisplayName("Get movie credits successful")
    void getMovieCreditsSuccessful() {
        when(tmdbClient.movieCredits(anyInt(), any(), any())).thenReturn(MediaBuilder.credits());

        CreditsDTO credits = tmdbProxy.getMovieCredits(1);

        assertThat(credits.getCrew()).isNotEmpty();
    }

    @Test
    @DisplayName("Get tv successful")
    void getTVSuccessful() {
        when(tmdbClient.tv(anyInt(), any(), any())).thenReturn(MediaBuilder.tv());

        TvDTO tv = tmdbProxy.getTV(1);

        assertThat(tv.getTitle()).isEqualTo(MediaBuilder.getTV().getTitle());
        assertThat(tv.getDateRelease()).isEqualTo(MediaBuilder.getTV().getDateRelease());
    }

}
