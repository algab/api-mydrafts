package br.com.mydrafts.apimydrafts.clients;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Production;
import br.com.mydrafts.apimydrafts.dto.TMDBCreditsDTO;
import br.com.mydrafts.apimydrafts.dto.TMDBMovieDTO;
import br.com.mydrafts.apimydrafts.dto.TMDBResponseDTO;
import br.com.mydrafts.apimydrafts.dto.TMDBTvDTO;
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

        TMDBResponseDTO trendingMovie = tmdbProxy.trendingMovie();

        assertThat(trendingMovie.getResults()).isNotEmpty();
    }

    @Test
    @DisplayName("Trending movie exception")
    void trendingMovieThrowException() {
        Request request = Request.create(Request.HttpMethod.GET, URL_EXCEPTION, new HashMap<>(), null, null, null);
        Response response = Response.builder().status(500).reason(MESSAGE_SERVER_ERROR).request(request).headers(new HashMap<>()).build();
        when(tmdbClient.trendingMovie(anyString(), anyString())).thenThrow(FeignException.errorStatus("", response));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> tmdbProxy.trendingMovie());
    }

    @Test
    @DisplayName("Trending tv successful")
    void trendingTVSuccessful() {
        when(tmdbClient.trendingTv(anyString(), anyString())).thenReturn(TrendingBuilder.responseTrendingTV());

        TMDBResponseDTO trendingTV = tmdbProxy.trendingTV();

        assertThat(trendingTV.getResults()).isNotEmpty();
    }

    @Test
    @DisplayName("Trending tv exception")
    void trendingTVThrowException() {
        Request request = Request.create(Request.HttpMethod.GET, URL_EXCEPTION, new HashMap<>(), null, null, null);
        Response response = Response.builder().status(500).reason(MESSAGE_SERVER_ERROR).request(request).headers(new HashMap<>()).build();
        when(tmdbClient.trendingTv(anyString(), anyString())).thenThrow(FeignException.errorStatus("", response));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> tmdbProxy.trendingTV());
    }

    @Test
    @DisplayName("Search movie successful")
    void searchMovieSuccessful() {
        when(tmdbClient.searchMovie(anyString(), anyString(), anyString())).thenReturn(SearchBuilder.responseSearchMovie());

        TMDBResponseDTO searchMovie = tmdbProxy.searchMovie(NAME_MOVIE);

        assertThat(searchMovie.getResults().size()).isEqualTo(SearchBuilder.responseSearchMovie().getResults().size());
    }

    @Test
    @DisplayName("Search movie exception")
    void searchMovieThrowException() {
        Request request = Request.create(Request.HttpMethod.GET, URL_EXCEPTION, new HashMap<>(), null, null, null);
        Response response = Response.builder().status(500).reason(MESSAGE_SERVER_ERROR).request(request).headers(new HashMap<>()).build();
        when(tmdbClient.searchMovie(anyString(), anyString(), anyString())).thenThrow(FeignException.errorStatus("", response));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> tmdbProxy.searchMovie(NAME_MOVIE));
    }

    @Test
    @DisplayName("Search tv successful")
    void searchTVSuccessful() {
        when(tmdbClient.searchTv(any(String.class), any(String.class), any(String.class))).thenReturn(SearchBuilder.responseSearchTV());

        TMDBResponseDTO searchTV = tmdbProxy.searchTV(NAME_TV_SHOW);

        assertThat(searchTV.getResults().size()).isEqualTo(SearchBuilder.responseSearchTV().getResults().size());
    }

    @Test
    @DisplayName("Search tv exception")
    void searchTVThrowException() {
        Request request = Request.create(Request.HttpMethod.GET, URL_EXCEPTION, new HashMap<>(), null, null, null);
        Response response = Response.builder().status(500).reason(MESSAGE_SERVER_ERROR).request(request).headers(new HashMap<>()).build();
        when(tmdbClient.searchTv(anyString(), anyString(), anyString())).thenThrow(FeignException.errorStatus("", response));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> tmdbProxy.searchTV(NAME_TV_SHOW));
    }

    @Test
    @DisplayName("Get movie successful")
    void getMovieSuccessful() {
        when(tmdbClient.movie(any(Integer.class), any(String.class), any(String.class))).thenReturn(MediaBuilder.movie());

        TMDBMovieDTO movie = tmdbProxy.getMovie(1);

        assertThat(movie.getTitle()).isEqualTo(MediaBuilder.getMovie().getTitle());
        assertThat(movie.getDateRelease()).isEqualTo(MediaBuilder.getMovie().getDateRelease());
    }

    @Test
    @DisplayName("Get movie exception")
    void getMovieThrowException() {
        Request request = Request.create(Request.HttpMethod.GET, URL_EXCEPTION, new HashMap<>(), null, null, null);
        Response response = Response.builder().status(500).reason(MESSAGE_SERVER_ERROR).request(request).headers(new HashMap<>()).build();
        when(tmdbClient.movie(any(Integer.class), anyString(), anyString())).thenThrow(FeignException.errorStatus("", response));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> tmdbProxy.getMovie(1));
    }

    @Test
    @DisplayName("Get movie credits successful")
    void getMovieCreditsSuccessful() {
        when(tmdbClient.movieCredits(any(Integer.class), anyString(), anyString())).thenReturn(MediaBuilder.credits());

        TMDBCreditsDTO credits = tmdbProxy.getMovieCredits(1);

        assertThat(credits.getCrew()).isNotEmpty();
    }

    @Test
    @DisplayName("Get movie credits exception")
    void getMovieCreditsThrowException() {
        Request request = Request.create(Request.HttpMethod.GET, URL_EXCEPTION, new HashMap<>(), null, null, null);
        Response response = Response.builder().status(500).reason(MESSAGE_SERVER_ERROR).request(request).headers(new HashMap<>()).build();
        when(tmdbClient.movieCredits(any(Integer.class), anyString(), anyString())).thenThrow(FeignException.errorStatus("", response));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> tmdbProxy.getMovieCredits(1));
    }

    @Test
    @DisplayName("Get tv successful")
    void getTVSuccessful() {
        when(tmdbClient.tv(any(Integer.class), anyString(), anyString())).thenReturn(MediaBuilder.tv());

        TMDBTvDTO tv = tmdbProxy.getTV(1);

        assertThat(tv.getTitle()).isEqualTo(MediaBuilder.getTV().getTitle());
        assertThat(tv.getDateRelease()).isEqualTo(MediaBuilder.getTV().getDateRelease());
    }

    @Test
    @DisplayName("Get tv exception")
    void getTVThrowException() {
        Request request = Request.create(Request.HttpMethod.GET, URL_EXCEPTION, new HashMap<>(), null, null, null);
        Response response = Response.builder().status(500).reason(MESSAGE_SERVER_ERROR).request(request).headers(new HashMap<>()).build();
        when(tmdbClient.tv(any(Integer.class), anyString(), anyString())).thenThrow(FeignException.errorStatus("", response));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> tmdbProxy.getTV(1));
    }

    @Test
    @DisplayName("Find production movie successful")
    void findProductionMovieShouldReturnSuccessful() {
        when(tmdbClient.movie(any(Integer.class), any(String.class), any(String.class))).thenReturn(MediaBuilder.movie());
        when(tmdbClient.movieCredits(any(Integer.class), anyString(), anyString())).thenReturn(MediaBuilder.credits());

        Production production = tmdbProxy.findProduction(Media.MOVIE, 1);

        assertThat(production.getTv()).isNull();
        assertThat(production.getMedia()).isEqualTo(Media.MOVIE);
        assertThat(production.getMovie().getTitle()).isEqualTo(MediaBuilder.movie().getTitle());
    }

    @Test
    @DisplayName("Find production tv successful")
    void findProductionTVShouldReturnSuccessful() {
        when(tmdbClient.tv(any(Integer.class), anyString(), anyString())).thenReturn(MediaBuilder.tv());

        Production production = tmdbProxy.findProduction(Media.TV, 1);

        assertThat(production.getMovie()).isNull();
        assertThat(production.getMedia()).isEqualTo(Media.TV);
        assertThat(production.getTv().getTitle()).isEqualTo(MediaBuilder.tv().getTitle());
    }

}
