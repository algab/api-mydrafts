package br.com.mydrafts.apimydrafts.clients;

import br.com.mydrafts.apimydrafts.dto.tmdb.CreditsDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvDTO;
import br.com.mydrafts.apimydrafts.fixtures.MediaFixture;
import br.com.mydrafts.apimydrafts.fixtures.SearchFixture;
import br.com.mydrafts.apimydrafts.fixtures.TrendingFixture;
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
        when(tmdbClient.trendingMovie(any(), any())).thenReturn(TrendingFixture.responseTrendingMovie());

        ResponseDTO trendingMovie = tmdbProxy.trendingMovie();

        assertThat(trendingMovie.getResults()).isNotEmpty();
    }

    @Test
    @DisplayName("Trending tv successful")
    void trendingTVSuccessful() {
        when(tmdbClient.trendingTv(any(), any())).thenReturn(TrendingFixture.responseTrendingTV());

        ResponseDTO trendingTV = tmdbProxy.trendingTV();

        assertThat(trendingTV.getResults()).isNotEmpty();
    }

    @Test
    @DisplayName("Search movie successful")
    void searchMovieSuccessful() {
        when(tmdbClient.searchMovie(any(), any(), anyString())).thenReturn(SearchFixture.responseSearchMovie());

        ResponseDTO searchMovie = tmdbProxy.searchMovie(NAME_MOVIE);

        assertThat(searchMovie.getResults()).hasSize(SearchFixture.responseSearchMovie().getResults().size());
    }

    @Test
    @DisplayName("Search tv successful")
    void searchTVSuccessful() {
        when(tmdbClient.searchTv(any(), any(), anyString())).thenReturn(SearchFixture.responseSearchTV());

        ResponseDTO searchTV = tmdbProxy.searchTV(NAME_TV_SHOW);

        assertThat(searchTV.getResults()).hasSize(SearchFixture.responseSearchTV().getResults().size());
    }

    @Test
    @DisplayName("Get movie successful")
    void getMovieSuccessful() {
        when(tmdbClient.movie(anyInt(), any(), any())).thenReturn(MediaFixture.movie());

        MovieDTO movie = tmdbProxy.getMovie(1);

        assertThat(movie.getTitle()).isEqualTo(MediaFixture.getMovie().getTitle());
        assertThat(movie.getDateRelease()).isEqualTo(MediaFixture.getMovie().getDateRelease());
    }

    @Test
    @DisplayName("Get movie credits successful")
    void getMovieCreditsSuccessful() {
        when(tmdbClient.movieCredits(anyInt(), any(), any())).thenReturn(MediaFixture.credits());

        CreditsDTO credits = tmdbProxy.getMovieCredits(1);

        assertThat(credits.getCrew()).isNotEmpty();
    }

    @Test
    @DisplayName("Get tv successful")
    void getTVSuccessful() {
        when(tmdbClient.tv(anyInt(), any(), any())).thenReturn(MediaFixture.tv());

        TvDTO tv = tmdbProxy.getTV(1);

        assertThat(tv.getTitle()).isEqualTo(MediaFixture.getTV().getTitle());
        assertThat(tv.getDateRelease()).isEqualTo(MediaFixture.getTV().getDateRelease());
    }

}
