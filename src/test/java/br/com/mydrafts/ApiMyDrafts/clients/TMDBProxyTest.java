package br.com.mydrafts.ApiMyDrafts.clients;

import br.com.mydrafts.ApiMyDrafts.dto.TMDBCreditsDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBMovieDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBResponseDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBTvDTO;
import br.com.mydrafts.ApiMyDrafts.utils.MediaUtil;
import br.com.mydrafts.ApiMyDrafts.utils.SearchUtil;
import br.com.mydrafts.ApiMyDrafts.utils.TrendingUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for TMDBProxy")
public class TMDBProxyTest {

    @Autowired
    private TMDBProxy tmdbProxy;

    @MockBean
    private TMDBClient tmdbClient;

    @Test
    public void trendingMovieSuccessful() {
        when(tmdbClient.trendingMovie(any(String.class), any(String.class))).thenReturn(TrendingUtil.responseTrendingMovie());

        TMDBResponseDTO trendingMovie = tmdbProxy.trendingMovie();

        assertThat(trendingMovie.getResults()).isNotEmpty();
    }

    @Test
    public void trendingTVSuccessful() {
        when(tmdbClient.trendingTv(any(String.class), any(String.class))).thenReturn(TrendingUtil.responseTrendingTV());

        TMDBResponseDTO trendingTV = tmdbProxy.trendingTV();

        assertThat(trendingTV.getResults()).isNotEmpty();
    }

    @Test
    public void searchMovieSuccessful() {
        when(tmdbClient.searchMovie(any(String.class), any(String.class), any(String.class))).thenReturn(SearchUtil.responseSearchMovie());

        TMDBResponseDTO searchMovie = tmdbProxy.searchMovie("Shang-Chi");

        assertThat(searchMovie.getResults().size()).isEqualTo(SearchUtil.responseSearchMovie().getResults().size());
    }

    @Test
    public void searchTVSuccessful() {
        when(tmdbClient.searchTv(any(String.class), any(String.class), any(String.class))).thenReturn(SearchUtil.responseSearchTV());

        TMDBResponseDTO searchTV = tmdbProxy.searchTV("What If");

        assertThat(searchTV.getResults().size()).isEqualTo(SearchUtil.responseSearchTV().getResults().size());
    }

    @Test
    public void getMovieSuccessful() {
        when(tmdbClient.movie(any(Integer.class), any(String.class), any(String.class))).thenReturn(MediaUtil.movie());

        TMDBMovieDTO movie = tmdbProxy.getMovie(1);

        assertThat(movie.getTitle()).isEqualTo(MediaUtil.getMovie().getTitle());
        assertThat(movie.getDateRelease()).isEqualTo(MediaUtil.getMovie().getDateRelease());
    }

    @Test
    public void getMovieCreditsSuccessful() {
        when(tmdbClient.movieCredits(any(Integer.class), any(String.class), any(String.class))).thenReturn(MediaUtil.credits());

        TMDBCreditsDTO credits = tmdbProxy.getMovieCredits(1);

        assertThat(credits.getCrew()).isNotEmpty();
    }

    @Test
    public void getTVSuccessful() {
        when(tmdbClient.tv(any(Integer.class), any(String.class), any(String.class))).thenReturn(MediaUtil.tv());

        TMDBTvDTO tv = tmdbProxy.getTV(1);

        assertThat(tv.getTitle()).isEqualTo(MediaUtil.getTV().getTitle());
        assertThat(tv.getDateRelease()).isEqualTo(MediaUtil.getTV().getDateRelease());
    }

}
