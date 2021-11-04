package br.com.mydrafts.ApiMyDrafts.clients;

import br.com.mydrafts.ApiMyDrafts.constants.Media;
import br.com.mydrafts.ApiMyDrafts.documents.Production;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBCreditsDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBMovieDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBResponseDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBTvDTO;
import br.com.mydrafts.ApiMyDrafts.exceptions.BusinessException;
import br.com.mydrafts.ApiMyDrafts.utils.MediaUtil;
import br.com.mydrafts.ApiMyDrafts.utils.SearchUtil;
import br.com.mydrafts.ApiMyDrafts.utils.TrendingUtil;
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
public class TMDBProxyTest {

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
    public void trendingMovieSuccessful() {
        when(tmdbClient.trendingMovie(anyString(), anyString())).thenReturn(TrendingUtil.responseTrendingMovie());

        TMDBResponseDTO trendingMovie = tmdbProxy.trendingMovie();

        assertThat(trendingMovie.getResults()).isNotEmpty();
    }

    @Test
    @DisplayName("Trending movie exception")
    public void trendingMovieThrowException() {
        Request request = Request.create(Request.HttpMethod.GET, URL_EXCEPTION, new HashMap<>(), null, null, null);
        Response response = Response.builder().status(500).reason(MESSAGE_SERVER_ERROR).request(request).headers(new HashMap<>()).build();
        when(tmdbClient.trendingMovie(anyString(), anyString())).thenThrow(FeignException.errorStatus("", response));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> tmdbProxy.trendingMovie());
    }

    @Test
    @DisplayName("Trending tv successful")
    public void trendingTVSuccessful() {
        when(tmdbClient.trendingTv(anyString(), anyString())).thenReturn(TrendingUtil.responseTrendingTV());

        TMDBResponseDTO trendingTV = tmdbProxy.trendingTV();

        assertThat(trendingTV.getResults()).isNotEmpty();
    }

    @Test
    @DisplayName("Trending tv exception")
    public void trendingTVThrowException() {
        Request request = Request.create(Request.HttpMethod.GET, URL_EXCEPTION, new HashMap<>(), null, null, null);
        Response response = Response.builder().status(500).reason(MESSAGE_SERVER_ERROR).request(request).headers(new HashMap<>()).build();
        when(tmdbClient.trendingTv(anyString(), anyString())).thenThrow(FeignException.errorStatus("", response));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> tmdbProxy.trendingTV());
    }

    @Test
    @DisplayName("Search movie successful")
    public void searchMovieSuccessful() {
        when(tmdbClient.searchMovie(anyString(), anyString(), anyString())).thenReturn(SearchUtil.responseSearchMovie());

        TMDBResponseDTO searchMovie = tmdbProxy.searchMovie(NAME_MOVIE);

        assertThat(searchMovie.getResults().size()).isEqualTo(SearchUtil.responseSearchMovie().getResults().size());
    }

    @Test
    @DisplayName("Search movie exception")
    public void searchMovieThrowException() {
        Request request = Request.create(Request.HttpMethod.GET, URL_EXCEPTION, new HashMap<>(), null, null, null);
        Response response = Response.builder().status(500).reason(MESSAGE_SERVER_ERROR).request(request).headers(new HashMap<>()).build();
        when(tmdbClient.searchMovie(anyString(), anyString(), anyString())).thenThrow(FeignException.errorStatus("", response));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> tmdbProxy.searchMovie(NAME_MOVIE));
    }

    @Test
    @DisplayName("Search tv successful")
    public void searchTVSuccessful() {
        when(tmdbClient.searchTv(any(String.class), any(String.class), any(String.class))).thenReturn(SearchUtil.responseSearchTV());

        TMDBResponseDTO searchTV = tmdbProxy.searchTV(NAME_TV_SHOW);

        assertThat(searchTV.getResults().size()).isEqualTo(SearchUtil.responseSearchTV().getResults().size());
    }

    @Test
    @DisplayName("Search tv exception")
    public void searchTVThrowException() {
        Request request = Request.create(Request.HttpMethod.GET, URL_EXCEPTION, new HashMap<>(), null, null, null);
        Response response = Response.builder().status(500).reason(MESSAGE_SERVER_ERROR).request(request).headers(new HashMap<>()).build();
        when(tmdbClient.searchTv(anyString(), anyString(), anyString())).thenThrow(FeignException.errorStatus("", response));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> tmdbProxy.searchTV(NAME_TV_SHOW));
    }

    @Test
    @DisplayName("Get movie successful")
    public void getMovieSuccessful() {
        when(tmdbClient.movie(any(Integer.class), any(String.class), any(String.class))).thenReturn(MediaUtil.movie());

        TMDBMovieDTO movie = tmdbProxy.getMovie(1);

        assertThat(movie.getTitle()).isEqualTo(MediaUtil.getMovie().getTitle());
        assertThat(movie.getDateRelease()).isEqualTo(MediaUtil.getMovie().getDateRelease());
    }

    @Test
    @DisplayName("Get movie exception")
    public void getMovieThrowException() {
        Request request = Request.create(Request.HttpMethod.GET, URL_EXCEPTION, new HashMap<>(), null, null, null);
        Response response = Response.builder().status(500).reason(MESSAGE_SERVER_ERROR).request(request).headers(new HashMap<>()).build();
        when(tmdbClient.movie(any(Integer.class), anyString(), anyString())).thenThrow(FeignException.errorStatus("", response));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> tmdbProxy.getMovie(1));
    }

    @Test
    @DisplayName("Get movie credits successful")
    public void getMovieCreditsSuccessful() {
        when(tmdbClient.movieCredits(any(Integer.class), anyString(), anyString())).thenReturn(MediaUtil.credits());

        TMDBCreditsDTO credits = tmdbProxy.getMovieCredits(1);

        assertThat(credits.getCrew()).isNotEmpty();
    }

    @Test
    @DisplayName("Get movie credits exception")
    public void getMovieCreditsThrowException() {
        Request request = Request.create(Request.HttpMethod.GET, URL_EXCEPTION, new HashMap<>(), null, null, null);
        Response response = Response.builder().status(500).reason(MESSAGE_SERVER_ERROR).request(request).headers(new HashMap<>()).build();
        when(tmdbClient.movieCredits(any(Integer.class), anyString(), anyString())).thenThrow(FeignException.errorStatus("", response));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> tmdbProxy.getMovieCredits(1));
    }

    @Test
    @DisplayName("Get tv successful")
    public void getTVSuccessful() {
        when(tmdbClient.tv(any(Integer.class), anyString(), anyString())).thenReturn(MediaUtil.tv());

        TMDBTvDTO tv = tmdbProxy.getTV(1);

        assertThat(tv.getTitle()).isEqualTo(MediaUtil.getTV().getTitle());
        assertThat(tv.getDateRelease()).isEqualTo(MediaUtil.getTV().getDateRelease());
    }

    @Test
    @DisplayName("Get tv exception")
    public void getTVThrowException() {
        Request request = Request.create(Request.HttpMethod.GET, URL_EXCEPTION, new HashMap<>(), null, null, null);
        Response response = Response.builder().status(500).reason(MESSAGE_SERVER_ERROR).request(request).headers(new HashMap<>()).build();
        when(tmdbClient.tv(any(Integer.class), anyString(), anyString())).thenThrow(FeignException.errorStatus("", response));

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> tmdbProxy.getTV(1));
    }

    @Test
    @DisplayName("Find production movie successful")
    public void findProductionMovieShouldReturnSuccessful() {
        when(tmdbClient.movie(any(Integer.class), any(String.class), any(String.class))).thenReturn(MediaUtil.movie());
        when(tmdbClient.movieCredits(any(Integer.class), anyString(), anyString())).thenReturn(MediaUtil.credits());

        Production production = tmdbProxy.findProduction(Media.MOVIE, 1);

        assertThat(production.getTv()).isNull();
        assertThat(production.getMedia()).isEqualTo(Media.MOVIE);
        assertThat(production.getMovie().getTitle()).isEqualTo(MediaUtil.movie().getTitle());
    }

    @Test
    @DisplayName("Find production tv successful")
    public void findProductionTVShouldReturnSuccessful() {
        when(tmdbClient.tv(any(Integer.class), anyString(), anyString())).thenReturn(MediaUtil.tv());

        Production production = tmdbProxy.findProduction(Media.TV, 1);

        assertThat(production.getMovie()).isNull();
        assertThat(production.getMedia()).isEqualTo(Media.TV);
        assertThat(production.getTv().getTitle()).isEqualTo(MediaUtil.tv().getTitle());
    }

}
