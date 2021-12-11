package br.com.mydrafts.apimydrafts.clients;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Production;
import br.com.mydrafts.apimydrafts.dto.tmdb.CreditsDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.builder.MediaBuilder;
import br.com.mydrafts.apimydrafts.builder.SearchBuilder;
import br.com.mydrafts.apimydrafts.builder.TrendingBuilder;
import feign.FeignException;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for TMDBProxy")
class TMDBProxyTest {

    @Autowired
    private TMDBProxy tmdbProxy;

    @MockBean
    private TMDBClient tmdbClient;

    private static final String URL_EXCEPTION = "/test";
    private static final String MESSAGE_SERVER_ERROR = "Server Error";
    private static final String NAME_MOVIE = "Shang-Chi";
    private static final String NAME_TV_SHOW = "What If";

    @Test
    @DisplayName("Trending movie successful")
    void trendingMovieSuccessful() {
        when(tmdbClient.trendingMovie(anyString(), anyString())).thenReturn(TrendingBuilder.responseTrendingMovie());

        ResponseDTO trendingMovie = tmdbProxy.trendingMovie();

        assertThat(trendingMovie.getResults()).isNotEmpty();
    }

    @Test
    @DisplayName("Trending tv successful")
    void trendingTVSuccessful() {
        when(tmdbClient.trendingTv(anyString(), anyString())).thenReturn(TrendingBuilder.responseTrendingTV());

        ResponseDTO trendingTV = tmdbProxy.trendingTV();

        assertThat(trendingTV.getResults()).isNotEmpty();
    }

    @Test
    @DisplayName("Search movie successful")
    void searchMovieSuccessful() {
        when(tmdbClient.searchMovie(anyString(), anyString(), anyString())).thenReturn(SearchBuilder.responseSearchMovie());

        ResponseDTO searchMovie = tmdbProxy.searchMovie(NAME_MOVIE);

        assertThat(searchMovie.getResults().size()).isEqualTo(SearchBuilder.responseSearchMovie().getResults().size());
    }

    @Test
    @DisplayName("Search tv successful")
    void searchTVSuccessful() {
        when(tmdbClient.searchTv(any(String.class), any(String.class), any(String.class))).thenReturn(SearchBuilder.responseSearchTV());

        ResponseDTO searchTV = tmdbProxy.searchTV(NAME_TV_SHOW);

        assertThat(searchTV.getResults().size()).isEqualTo(SearchBuilder.responseSearchTV().getResults().size());
    }

    @Test
    @DisplayName("Get movie successful")
    void getMovieSuccessful() {
        when(tmdbClient.movie(any(Integer.class), any(String.class), any(String.class))).thenReturn(MediaBuilder.movie());

        MovieDTO movie = tmdbProxy.getMovie(1);

        assertThat(movie.getTitle()).isEqualTo(MediaBuilder.getMovie().getTitle());
        assertThat(movie.getDateRelease()).isEqualTo(MediaBuilder.getMovie().getDateRelease());
    }

    @Test
    @DisplayName("Get movie credits successful")
    void getMovieCreditsSuccessful() {
        when(tmdbClient.movieCredits(any(Integer.class), anyString(), anyString())).thenReturn(MediaBuilder.credits());

        CreditsDTO credits = tmdbProxy.getMovieCredits(1);

        assertThat(credits.getCrew()).isNotEmpty();
    }

    @Test
    @DisplayName("Get tv successful")
    void getTVSuccessful() {
        when(tmdbClient.tv(any(Integer.class), anyString(), anyString())).thenReturn(MediaBuilder.tv());

        TvDTO tv = tmdbProxy.getTV(1);

        assertThat(tv.getTitle()).isEqualTo(MediaBuilder.getTV().getTitle());
        assertThat(tv.getDateRelease()).isEqualTo(MediaBuilder.getTV().getDateRelease());
    }

}
